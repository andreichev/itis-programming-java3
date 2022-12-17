import ru.itis.game.levels.FirstLevel;
import ru.itis.gengine.application.Application;
import ru.itis.gengine.application.ApplicationStartupSettings;
import ru.itis.gengine.base.GSize;

public class Main {
    public static void main(String[] args) {
        Application.shared.run(
                ApplicationStartupSettings.builder()
                        .name("GLGame")
                        .windowTitle("World of Tanks")
                        .startupLevel(new FirstLevel())
                        .windowSize(new GSize(500, 600))
                        .isFullScreen(false)
                        .build()
        );
    }
}