package no.nordli.eventadapter.example;

import no.nordli.eventadapter.Event;
import no.nordli.eventadapter.EventBus;

/**
 * GitHubber
 * Test class
 * Created by Brynje Nordli on 06/09/16.
 */
class GitHubber implements Comparable<GitHubber>, Event {
    private String email;
    private Fanaticism githHubLevel;

    @Override
    public int compareTo(GitHubber another) {
        if (another == null) return -1;
        return email().compareTo(another.email());
    }

    @Override
    public void notifyObjectChanged() {
        EventBus.getInstance().notifyObjectChanged(GitHubber.class.getName(), this);
    }

    Fanaticism getRandomFanaticism() {
        return Fanaticism.values()[(int) Math.round((2*Math.random()))];
    }

    enum Fanaticism {
        Meh, Regular, Avid, MadMan
    }

    GitHubber(String email, Fanaticism fanaticism) {
        this.email = email;
        this.githHubLevel = fanaticism;
    }

    void setFanaticism(Fanaticism fanaticism) {
        if (githHubLevel != fanaticism) {
            this.githHubLevel = fanaticism;
            notifyObjectChanged();
        }
    }

    void setEmail(String newEmail) {
        if (email != null && !email.equals(newEmail)) {
            email = newEmail;
            notifyObjectChanged();
        }
    }

    String email() {
        return email;
    }

    Fanaticism gitHubLevel() {
        return githHubLevel;
    }
}