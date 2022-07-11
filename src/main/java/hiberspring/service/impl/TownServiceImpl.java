package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.common.GlobalConstants;
import hiberspring.models.Town;
import hiberspring.models.dto.json.TownDto;
import hiberspring.repository.TownRepository;
import hiberspring.service.TownService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean townsAreImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return String.join("", Files.readAllLines(Path.of(GlobalConstants.TOWNS_FILE_PATH)));
    }

    @Override
    public String importTowns(String townsFileContent) throws IOException {

        StringBuilder sb = new StringBuilder();

        TownDto[] townDtos = this.gson.fromJson(this.readTownsJsonFile(), TownDto[].class);

        for (TownDto townDto : townDtos) {

            Town byName = townRepository.findByName(townDto.getName());


            if (this.validationUtil.isValid(townDto) && byName == null) {

                townRepository.saveAndFlush(this.modelMapper.map(townDto, Town.class));

                sb.append(String
                        .format(GlobalConstants.SUCCESSFUL_IMPORT_MESSAGE, "Town", townDto.getName()))
                        .append(System.lineSeparator());



            } else {
                sb.append(GlobalConstants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
            }
        }


        return sb.toString();
    }
}
