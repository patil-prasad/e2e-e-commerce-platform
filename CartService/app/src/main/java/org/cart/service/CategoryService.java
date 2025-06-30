package org.cart.service;

import org.cart.entity.Category;
import org.cart.models.CategoryRequestDTO;
import org.cart.models.CategoryResponseDTO;
import org.cart.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CategoryResponseDTO save(CategoryRequestDTO categoryRequestDTO) {
        Category category = Category.builder()
                .name(categoryRequestDTO.getName().toUpperCase(Locale.ENGLISH))
                .build();
        return modelMapper.map(categoryRepository.save(category), CategoryResponseDTO.class);
    }

    public Page<CategoryResponseDTO> findAll(PageRequest pageRequest) {
        return categoryRepository.findAll(pageRequest)
                .map(category -> modelMapper.map(category, CategoryResponseDTO.class));
    }


    @Transactional
    public CategoryResponseDTO update(Long id, CategoryRequestDTO categoryRequestDTO) {
        Category category = Category.builder()
                .id(id)
                .name(categoryRequestDTO.getName().toUpperCase(Locale.ENGLISH))
                .build();
        return modelMapper.map(categoryRepository.save(category), CategoryResponseDTO.class);
    }
}
