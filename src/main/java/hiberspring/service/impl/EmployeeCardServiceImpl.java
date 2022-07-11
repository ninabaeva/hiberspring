package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.common.GlobalConstants;
import hiberspring.models.EmployeeCard;
import hiberspring.models.dto.json.CardDto;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.service.EmployeeCardService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class EmployeeCardServiceImpl implements EmployeeCardService {

    private final EmployeeCardRepository employeeCardRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public EmployeeCardServiceImpl(EmployeeCardRepository employeeCardRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.employeeCardRepository = employeeCardRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean employeeCardsAreImported() {
        return employeeCardRepository.count() > 0;
    }

    @Override
    public String readEmployeeCardsJsonFile() throws IOException {
        return String.join("", Files.readAllLines(Path.of(GlobalConstants.EMPLOYEE_CARDS_FILE_PATH)));
    }

    @Override
    public String importEmployeeCards(String employeeCardsFileContent) throws IOException {
        StringBuilder sb = new StringBuilder();

        CardDto[] cardDtos = this.gson.fromJson(this.readEmployeeCardsJsonFile(), CardDto[].class);

        for (CardDto cardDto : cardDtos) {

            EmployeeCard byNumber = employeeCardRepository.findByNumber(cardDto.getNumber());


            if (this.validationUtil.isValid(cardDto) && byNumber == null) {

                employeeCardRepository.saveAndFlush(this.modelMapper.map(cardDto, EmployeeCard.class));

                sb.append(String
                        .format(GlobalConstants.SUCCESSFUL_IMPORT_MESSAGE, "Employee Card", cardDto.getNumber()))
                        .append(System.lineSeparator());



            } else {
                sb.append(GlobalConstants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
            }
        }

        return sb.toString();
    }
}
