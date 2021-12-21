package guru.springframework.recipe.controllers;

import guru.springframework.recipe.commands.RecipeCommand;
import guru.springframework.recipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    ImageController imageController;

    @Mock
    RecipeService recipeService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    void renderImage() throws Exception {
        //given
        var bytes = "Some Bytes".getBytes();
        var image = new Byte[bytes.length];
        for (int i = 0; i < bytes.length; i++)
            image[i] = bytes[i];

        var recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);
        recipeCommand.setImage(image);

        when(recipeService.findCommandById(eq(1L))).thenReturn(recipeCommand);

        //when
        var response = mockMvc.perform(get("/recipe/1/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        assertEquals(image.length, response.getContentAsByteArray().length);
    }
}