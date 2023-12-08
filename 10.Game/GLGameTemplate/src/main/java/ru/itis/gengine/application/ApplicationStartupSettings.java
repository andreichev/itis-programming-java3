package ru.itis.gengine.application;

import ru.itis.gengine.base.GSize;
import ru.itis.gengine.gamelogic.EmptyLevel;
import ru.itis.gengine.gamelogic.LevelBase;

public class ApplicationStartupSettings {
    private String name;
    private String windowTitle;
    private GSize windowSize;
    private boolean isFullScreen;
    private LevelBase startupLevel;

    public String getName() {
        if (name == null) { return "No name"; }
        return name;
    }

    public String getWindowTitle() {
        if (windowTitle == null) { return ""; }
        return windowTitle;
    }

    public GSize getWindowSize() {
        if (windowSize == null) { return new GSize(900, 600); }
        return windowSize;
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public LevelBase getStartupLevel() {
        if (startupLevel == null) {
            startupLevel = new EmptyLevel();
        }
        return startupLevel;
    }

    ApplicationStartupSettings(String name, String windowTitle, GSize windowSize, boolean isFullScreen, LevelBase startupLevel) {
        this.name = name;
        this.windowTitle = windowTitle;
        this.windowSize = windowSize;
        this.isFullScreen = isFullScreen;
        this.startupLevel = startupLevel;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String windowTitle;
        private GSize windowSize;
        private boolean isFullScreen;
        private LevelBase startupLevel;

        private Builder() {}

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder windowTitle(String windowTitle) {
            this.windowTitle = windowTitle;
            return this;
        }

        public Builder windowSize(GSize windowSize) {
            this.windowSize = windowSize;
            return this;
        }

        public Builder isFullScreen(boolean isFullScreen) {
            this.isFullScreen = isFullScreen;
            return this;
        }

        public Builder startupLevel(LevelBase startupLevel) {
            this.startupLevel = startupLevel;
            return this;
        }

        public ApplicationStartupSettings build() {
            return new ApplicationStartupSettings(name, windowTitle, windowSize, isFullScreen, startupLevel);
        }
    }
}
