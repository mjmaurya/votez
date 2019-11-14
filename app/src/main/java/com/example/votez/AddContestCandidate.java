package com.example.votez;

public class AddContestCandidate {
    String contestName,contestType;
    String candidateName,candidateMobile,candidateBio,candidateImage,count;

    public AddContestCandidate() {
    }

    public AddContestCandidate(String contestName, String contestType) {
        this.contestName = contestName;
        this.contestType = contestType;
    }

    public AddContestCandidate(String candidateName, String candidateMobile, String candidateBio, String candidateImage,String count) {
        this.candidateName = candidateName;
        this.candidateMobile = candidateMobile;
        this.candidateBio = candidateBio;
        this.candidateImage = candidateImage;
        this.count=count;
    }

    public String getContestName() {
        return contestName;
    }

    public String getContestType() {
        return contestType;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public String getCandidateMobile() {
        return candidateMobile;
    }

    public String getCandidateBio() {
        return candidateBio;
    }

    public String getCandidateImage() {
        return candidateImage;
    }

    public String getCount() {
        return count;
    }
}
