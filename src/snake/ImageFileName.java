package snake;

import snakeGUI.Settings;
import snakeGUI.TypeOfElement;

import java.lang.annotation.*;

@Target(value=ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageFileName {
    String[] fileNames() default Settings.DEFAULT_IMAGE_FILE_NAME;
    TypeOfElement type() default TypeOfElement.BLOCK;
}
