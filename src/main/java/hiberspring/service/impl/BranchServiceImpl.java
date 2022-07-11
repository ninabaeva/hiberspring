package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.common.GlobalConstants;
import hiberspring.models.Branch;
import hiberspring.models.Town;
import hiberspring.models.dto.json.BranchDto;
import hiberspring.repository.BranchRepository;
import hiberspring.repository.TownRepository;
import hiberspring.service.BranchService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public BranchServiceImpl(BranchRepository branchRepository, TownRepository townRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.branchRepository = branchRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean branchesAreImported() {
        return branchRepository.count() > 0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return String.join("", Files.readAllLines(Path.of(GlobalConstants.BRANCHES_FILE_PATH)));
    }

    @Override
    public String importBranches(String branchesFileContent) throws IOException {
        StringBuilder sb = new StringBuilder();

        BranchDto[] branchDtos = this.gson.fromJson(this.readBranchesJsonFile(), BranchDto[].class);

        for (BranchDto branchDto : branchDtos) {

            Town byName = townRepository.findByName(branchDto.getTown());


            if (this.validationUtil.isValid(branchDto) && byName != null) {

                Branch branch = this.modelMapper.map(branchDto, Branch.class);



                branch.setTown(byName);

                branchRepository.saveAndFlush(branch);

                sb.append(String
                        .format(GlobalConstants.SUCCESSFUL_IMPORT_MESSAGE, "Branch", branch.getName()))
                        .append(System.lineSeparator());



            } else {
                sb.append(GlobalConstants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
            }
        }

        return sb.toString();
    }
}
