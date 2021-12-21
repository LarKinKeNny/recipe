package guru.springframework.recipe.services;

import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @InjectMocks
    ImageServiceImpl imageService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void saveImageFile() throws IOException {
        //given
        Long id = 1L;
        var multipartFile = new MockMultipartFile("imagefile", "testing.txt",
                "text/plain", "Some Bytes".getBytes());

        var recipe = new Recipe();
        recipe.setId(id);

        when(recipeRepository.findById(eq(id))).thenReturn(Optional.of(recipe));

        var argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //when
        imageService.saveImageFile(id, multipartFile);

        //then
        verify(recipeRepository).save(argumentCaptor.capture());
        assertEquals(multipartFile.getBytes().length, argumentCaptor.getValue().getImage().length);
    }
}