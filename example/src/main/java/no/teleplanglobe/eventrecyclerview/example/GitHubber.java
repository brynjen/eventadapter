package no.teleplanglobe.eventrecyclerview.example;

import no.teleplanglobe.eventrecyclerview.Event;
import no.teleplanglobe.eventrecyclerview.EventBus;

/**
 * GitHubber
 * Test class
 * Created by Brynje Nordli on 06/09/16.
 */
public class GitHubber implements Comparable<GitHubber>, Event {
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

    public Fanaticism getRandomFanaticism() {
        return Fanaticism.values()[(int) Math.round((2*Math.random()))];
    }

    public enum Fanaticism {
        Meh, Regular, Avid, MadMan
    }

    public GitHubber(String email, Fanaticism fanaticism) {
        this.email = email;
        this.githHubLevel = fanaticism;
    }

    public void setFanaticism(Fanaticism fanaticism) {
        if (githHubLevel != fanaticism) {
            this.githHubLevel = fanaticism;
            notifyObjectChanged();
        }
    }

    public void setEmail(String newEmail) {
        if (email != null && !email.equals(newEmail)) {
            email = newEmail;
            notifyObjectChanged();
        }
    }

    public String email() {
        return email;
    }

    public Fanaticism githHubLevel() {
        return githHubLevel;
    }
}