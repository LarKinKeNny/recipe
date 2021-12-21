package guru.springframework.recipe.services;

import guru.springframework.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long id, MultipartFile file) {
        var optionalRecipe = recipeRepository.findById(id);

        if (optionalRecipe.isPresent()) {
            var recipe = optionalRecipe.get();

            try {
                var bytes = file.getBytes();
                var byteObjects = new Byte[bytes.length];
                for (int i = 0; i < bytes.length; i++)
                    byteObjects[i] = bytes[i];

                recipe.setImage(byteObjects);
                recipeRepository.save(recipe);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
