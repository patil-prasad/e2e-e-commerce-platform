package org.catalog.service;


import org.catalog.entity.Category;
import org.catalog.entity.Product;
import org.catalog.models.ProductRequestDTO;
import org.catalog.repository.CategoryRepository;
import org.catalog.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.catalog.models.ProductResponseDTO;
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Autowired
    private ModelMapper modelMapper;

    public Page<ProductResponseDTO> findByCategoryName(String name, Pageable pageable) {
        return productRepository.findByCategoryName(name, pageable)
                .map(product -> modelMapper.map(product, ProductResponseDTO.class));
    }

    public ProductResponseDTO save(ProductRequestDTO productRequestDTO) {
        Category category = categoryRepository.findById(productRequestDTO.getCategoryId())
                    .orElseThrow();
        Product product = Product.builder()
                    .category(category)
                .name(productRequestDTO.getName())
                .description(productRequestDTO.getDescription())
                .price(productRequestDTO.getPrice())
                .quantity(productRequestDTO.getQuantity())
                .build();
        return modelMapper.map(productRepository.save(product), ProductResponseDTO.class);
    }


    @Transactional
    public ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow();
        Category category = categoryRepository.findById(productRequestDTO.getCategoryId())
                    .orElseThrow();
        product.setCategory(category);
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        product.setQuantity(productRequestDTO.getQuantity());
        return modelMapper.map(productRepository.save(product), ProductResponseDTO.class);
    }


    @Transactional
    public ProductResponseDTO buyProduct(Long id, Integer quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow();
        if(product.getQuantity() < quantity) {
            throw new RuntimeException("Not enough quantity");
        }
        product.setQuantity(product.getQuantity() - quantity);
        return modelMapper.map(productRepository.save(product), ProductResponseDTO.class);
    }

    public ProductResponseDTO findById(Long id) {
        return modelMapper.map(productRepository.findById(id)
                .orElseThrow(), ProductResponseDTO.class);
    }

    public Page<ProductResponseDTO> findAll(PageRequest of) {
        return productRepository.findAll(of)
                .map(product -> modelMapper.map(product, ProductResponseDTO.class));
    }
}
