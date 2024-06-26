package com.proyectointegrador.msevents;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectointegrador.msevents.domain.Category;
import com.proyectointegrador.msevents.dto.CategoryDTO;
import com.proyectointegrador.msevents.repository.ICategoryRepository;
import com.proyectointegrador.msevents.service.implement.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

	@Mock
	private ICategoryRepository categoryRepository;

	@Mock
	private ObjectMapper mapper;

	@InjectMocks
	private CategoryService categoryService;

	private Category category;
	private CategoryDTO categoryDTO;

	@BeforeEach
	void setUp() {
		category = new Category();
		category.setId(1L);
		category.setName("Music");

		categoryDTO = new CategoryDTO();
		categoryDTO.setId(1L);
		categoryDTO.setName("Music");
	}

	@Test
	void getCategoryById_shouldReturnCategoryDTO() {
		when(categoryRepository.findCategoryById(1L)).thenReturn(Optional.of(category));
		when(mapper.convertValue(eq(Optional.of(category)), eq(CategoryDTO.class))).thenReturn(categoryDTO);
		Optional<CategoryDTO> result = categoryService.getCategoryById(1L);
		assertTrue(result.isPresent());
		assertEquals(categoryDTO, result.get());
		verify(categoryRepository).findCategoryById(1L);
		verify(mapper).convertValue(eq(Optional.of(category)), eq(CategoryDTO.class));
	}



	@Test
	void getCategoryById_shouldReturnEmptyWhenNotFound() {
		when(categoryRepository.findCategoryById(1L)).thenReturn(Optional.empty());

		Optional<CategoryDTO> result = categoryService.getCategoryById(1L);

		assertFalse(result.isPresent());
		verify(categoryRepository).findCategoryById(1L);
	}

	@Test
	void getCategoryByName_shouldReturnCategoryDTO() {
		when(categoryRepository.findCategoryByName("Music")).thenReturn(Optional.of(category));
		when(mapper.convertValue(eq(Optional.of(category)), eq(CategoryDTO.class))).thenReturn(categoryDTO);
		Optional<CategoryDTO> result = categoryService.getCategoryByName("Music");
		assertTrue(result.isPresent());
		assertEquals(categoryDTO, result.get());
		verify(categoryRepository).findCategoryByName("Music");
		verify(mapper).convertValue(eq(Optional.of(category)), eq(CategoryDTO.class));
	}


	@Test
	void getCategoryByName_shouldReturnEmptyWhenNotFound() {
		when(categoryRepository.findCategoryByName("Music")).thenReturn(Optional.empty());

		Optional<CategoryDTO> result = categoryService.getCategoryByName("Music");

		assertFalse(result.isPresent());
		verify(categoryRepository).findCategoryByName("Music");
	}

	@Test
	void getAllCategories_shouldReturnCategoryDTOs() {
		List<Category> categories = Collections.singletonList(category);
		when(categoryRepository.findAll()).thenReturn(categories);
		when(mapper.convertValue(category, CategoryDTO.class)).thenReturn(categoryDTO);

		Set<CategoryDTO> result = categoryService.getAllCategories();

		assertEquals(1, result.size());
		assertTrue(result.contains(categoryDTO));
		verify(categoryRepository).findAll();
		verify(mapper, times(1)).convertValue(category, CategoryDTO.class);
	}

	@Test
	void addCategory_shouldSaveAndReturnCategoryDTO() {
		when(mapper.convertValue(categoryDTO, Category.class)).thenReturn(category);
		when(categoryRepository.save(category)).thenReturn(category);
		when(mapper.convertValue(category, CategoryDTO.class)).thenReturn(categoryDTO);

		CategoryDTO result = categoryService.addCategory(categoryDTO);

		assertEquals(categoryDTO, result);
		verify(categoryRepository).save(category);
		verify(mapper).convertValue(categoryDTO, Category.class);
		verify(mapper).convertValue(category, CategoryDTO.class);
	}

	@Test
	void updateCategory_shouldUpdateAndReturnCategoryDTO() {
		when(mapper.convertValue(categoryDTO, Category.class)).thenReturn(category);
		when(categoryRepository.save(category)).thenReturn(category);
		when(mapper.convertValue(category, CategoryDTO.class)).thenReturn(categoryDTO);

		CategoryDTO result = categoryService.updateCategory(categoryDTO);

		assertEquals(categoryDTO, result);
		verify(categoryRepository).save(category);
		verify(mapper).convertValue(categoryDTO, Category.class);
		verify(mapper).convertValue(category, CategoryDTO.class);
	}

	@Test
	void deleteCategoryById_shouldDeleteCategory() {
		doNothing().when(categoryRepository).deleteById(1L);

		categoryService.deleteCategoryById(1L);

		verify(categoryRepository).deleteById(1L);
	}

	@Test
	void deleteCategoryByName_shouldDeleteCategory() {
		when(categoryRepository.findCategoryByName("Music")).thenReturn(Optional.of(category));
		doNothing().when(categoryRepository).delete(category);

		categoryService.deleteCategoryByName("Music");

		verify(categoryRepository).findCategoryByName("Music");
		verify(categoryRepository).delete(category);
	}
}

