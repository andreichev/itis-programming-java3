import ru.itis.game.levels.FirstLevel;
import ru.itis.gengine.application.Application;
import ru.itis.gengine.application.ApplicationStartupSettings;
import ru.itis.gengine.base.GSize;

public class Main {
    public static void main(String[] args) {
        boolean isServer = args.length > 0 && "start_server".equals(args[0]);
        Application.shared.run(
                ApplicationStartupSettings.builder()
                        .name("GAME")
                        .windowTitle(isServer ? "SERVER" : "CLIENT")
                        .startupLevel(new FirstLevel())
                        .windowSize(new GSize(1200, 900))
                        .isFullScreen(false)
                        .isServer(isServer)
                        .build()
        );
    }
}