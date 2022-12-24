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
    private boolean isServer;

    public String getName() {
        if (name == null) { return "No name"; }
        return name;
    }

    public String getWindowTitle() {
        if (windowTitle == null) { return ""; }
        return windowTitle;
    }

    public GSize getWindowSize() {
        if (windowSize == null) { return new GSize(50, 50); }
        return windowSize;
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public boolean isServer() {
        return isServer;
    }

    public LevelBase getStartupLevel() {
        if (startupLevel == null) {
            startupLevel = new EmptyLevel();
        }
        return startupLevel;
    }

    ApplicationStartupSettings(String name, String windowTitle, GSize windowSize, boolean isFullScreen, LevelBase startupLevel, boolean isServer) {
        this.name = name;
        this.windowTitle = windowTitle;
        this.windowSize = windowSize;
        this.isFullScreen = isFullScreen;
        this.startupLevel = startupLevel;
        this.isServer = isServer;
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
        private boolean isServer;

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

        public Builder isServer(boolean isServer) {
            this.isServer = isServer;
            return this;
        }

        public ApplicationStartupSettings build() {
            return new ApplicationStartupSettings(name, windowTitle, windowSize, isFullScreen, startupLevel, isServer);
        }
    }
}
