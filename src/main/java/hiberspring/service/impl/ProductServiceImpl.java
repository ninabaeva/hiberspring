package hiberspring.service.impl;

import hiberspring.common.GlobalConstants;
import hiberspring.models.Branch;
import hiberspring.models.Product;
import hiberspring.models.dto.xml.ProductDto;
import hiberspring.models.dto.xml.ProductRootDto;
import hiberspring.repository.BranchRepository;
import hiberspring.repository.ProductRepository;
import hiberspring.service.ProductService;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ProductServiceImpl implements ProductService {



    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public ProductServiceImpl(ProductRepository productRepository, BranchRepository branchRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.productRepository = productRepository;
        this.branchRepository = branchRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean productsAreImported() {
        return productRepository.count() > 0;
    }

    @Override
    public String readProductsXmlFile() throws IOException {
        return String.join("", Files.readAllLines(Path.of(GlobalConstants.PRODUCTS_FILE_PATH)));
    }

    @Override
    public String importProducts() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        ProductRootDto productRootDto = this.xmlParser.parseXml(ProductRootDto.class, GlobalConstants.PRODUCTS_FILE_PATH);

        for (ProductDto productDto : productRootDto.getProducts()) {

            Product product = this.modelMapper.map(productDto, Product.class);

            Branch branch = this.branchRepository.findByName(productDto.getBranch());

            if (validationUtil.isValid(productDto) && branch != null) {
                product.setBranch(branch);

                    this.productRepository.saveAndFlush(product);

                    sb.append(String.format(GlobalConstants.SUCCESSFUL_IMPORT_MESSAGE, "Product", product.getName()))
                            .append(System.lineSeparator());



            } else {

                sb.append(GlobalConstants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
            }

        }
        return sb.toString();


    }
}
