package hiberspring.service.impl;

import hiberspring.common.GlobalConstants;
import hiberspring.models.Branch;
import hiberspring.models.Employee;
import hiberspring.models.EmployeeCard;
import hiberspring.models.Product;
import hiberspring.models.dto.xml.EmployeeDto;
import hiberspring.models.dto.xml.EmployeeRootDto;
import hiberspring.models.dto.xml.ProductDto;
import hiberspring.models.dto.xml.ProductRootDto;
import hiberspring.repository.BranchRepository;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.repository.EmployeeRepository;
import hiberspring.service.EmployeeService;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeCardRepository employeeCardRepository;
    private final BranchRepository branchRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeCardRepository employeeCardRepository, BranchRepository branchRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.employeeRepository = employeeRepository;
        this.employeeCardRepository = employeeCardRepository;
        this.branchRepository = branchRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean employeesAreImported() {
        return employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return String.join("", Files.readAllLines(Path.of(GlobalConstants.EMPLOYEES_FILE_PATH)));
    }

    @Override
    public String importEmployees() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        EmployeeRootDto employeeRootDto = this.xmlParser.parseXml(EmployeeRootDto.class, GlobalConstants.EMPLOYEES_FILE_PATH);

        for (EmployeeDto employeeDto : employeeRootDto.getEmployees()) {

            Employee employee = this.modelMapper.map(employeeDto, Employee.class);

            Branch branch = this.branchRepository.findByName(employeeDto.getBranch());
            EmployeeCard card = this.employeeCardRepository.findByNumber(employeeDto.getCard());

            if (validationUtil.isValid(employeeDto) && branch != null && card != null) {
                employee.setBranch(branch);
                employee.setCard(card);

                this.employeeRepository.saveAndFlush(employee);

                sb.append(String.format(GlobalConstants.SUCCESSFUL_IMPORT_MESSAGE, "Employee", employeeDto.getFirstName() + employee.getLastName()))
                        .append(System.lineSeparator());



            } else {

                sb.append(GlobalConstants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
            }

        }
        return sb.toString();
    }

    @Override
    public String exportProductiveEmployees() {
        StringBuilder sb = new StringBuilder();

        Set<Employee> employees = this.employeeRepository.export();

        for (Employee employee : employees) {
            sb.append(String.format("Name: %s %s", employee.getFirstName(), employee.getLastName())).append(System.lineSeparator());
            sb.append(String.format("Position: %s", employee.getPosition())).append(System.lineSeparator());
            sb.append(String.format("Card number: %s", employee.getCard().getNumber())).append(System.lineSeparator());
            sb.append("-------------------------").append(System.lineSeparator());
        }

        return sb.toString();

    }
}
