package com.example.gaurav.gitfetchapp.Repositories;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRepoJson implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("owner")
    @Expose
    private Owner owner;
    @SerializedName("private")
    @Expose
    private Boolean _private;
    @SerializedName("html_url")
    @Expose
    private String htmlUrl;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("fork")
    @Expose
    private Boolean fork;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("forks_url")
    @Expose
    private String forksUrl;
    @SerializedName("keys_url")
    @Expose
    private String keysUrl;
    @SerializedName("collaborators_url")
    @Expose
    private String collaboratorsUrl;
    @SerializedName("teams_url")
    @Expose
    private String teamsUrl;
    @SerializedName("hooks_url")
    @Expose
    private String hooksUrl;
    @SerializedName("issue_events_url")
    @Expose
    private String issueEventsUrl;
    @SerializedName("events_url")
    @Expose
    private String eventsUrl;
    @SerializedName("assignees_url")
    @Expose
    private String assigneesUrl;
    @SerializedName("branches_url")
    @Expose
    private String branchesUrl;
    @SerializedName("tags_url")
    @Expose
    private String tagsUrl;
    @SerializedName("blobs_url")
    @Expose
    private String blobsUrl;
    @SerializedName("git_tags_url")
    @Expose
    private String gitTagsUrl;
    @SerializedName("git_refs_url")
    @Expose
    private String gitRefsUrl;
    @SerializedName("trees_url")
    @Expose
    private String treesUrl;
    @SerializedName("statuses_url")
    @Expose
    private String statusesUrl;
    @SerializedName("languages_url")
    @Expose
    private String languagesUrl;
    @SerializedName("stargazers_url")
    @Expose
    private String stargazersUrl;
    @SerializedName("contributors_url")
    @Expose
    private String contributorsUrl;
    @SerializedName("subscribers_url")
    @Expose
    private String subscribersUrl;
    @SerializedName("subscription_url")
    @Expose
    private String subscriptionUrl;
    @SerializedName("commits_url")
    @Expose
    private String commitsUrl;
    @SerializedName("git_commits_url")
    @Expose
    private String gitCommitsUrl;
    @SerializedName("comments_url")
    @Expose
    private String commentsUrl;
    @SerializedName("issue_comment_url")
    @Expose
    private String issueCommentUrl;
    @SerializedName("contents_url")
    @Expose
    private String contentsUrl;
    @SerializedName("compare_url")
    @Expose
    private String compareUrl;
    @SerializedName("merges_url")
    @Expose
    private String mergesUrl;
    @SerializedName("archive_url")
    @Expose
    private String archiveUrl;
    @SerializedName("downloads_url")
    @Expose
    private String downloadsUrl;
    @SerializedName("issues_url")
    @Expose
    private String issuesUrl;
    @SerializedName("pulls_url")
    @Expose
    private String pullsUrl;
    @SerializedName("milestones_url")
    @Expose
    private String milestonesUrl;
    @SerializedName("notifications_url")
    @Expose
    private String notificationsUrl;
    @SerializedName("labels_url")
    @Expose
    private String labelsUrl;
    @SerializedName("releases_url")
    @Expose
    private String releasesUrl;
    @SerializedName("deployments_url")
    @Expose
    private String deploymentsUrl;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("pushed_at")
    @Expose
    private String pushedAt;
    @SerializedName("git_url")
    @Expose
    private String gitUrl;
    @SerializedName("ssh_url")
    @Expose
    private String sshUrl;
    @SerializedName("clone_url")
    @Expose
    private String cloneUrl;
    @SerializedName("svn_url")
    @Expose
    private String svnUrl;
    @SerializedName("homepage")
    @Expose
    private Object homepage;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("stargazers_count")
    @Expose
    private Integer stargazersCount;
    @SerializedName("watchers_count")
    @Expose
    private Integer watchersCount;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("has_issues")
    @Expose
    private Boolean hasIssues;
    @SerializedName("has_downloads")
    @Expose
    private Boolean hasDownloads;
    @SerializedName("has_wiki")
    @Expose
    private Boolean hasWiki;
    @SerializedName("has_pages")
    @Expose
    private Boolean hasPages;
    @SerializedName("forks_count")
    @Expose
    private Integer forksCount;
    @SerializedName("mirror_url")
    @Expose
    private Object mirrorUrl;
    @SerializedName("open_issues_count")
    @Expose
    private Integer openIssuesCount;
    @SerializedName("forks")
    @Expose
    private Integer forks;
    @SerializedName("open_issues")
    @Expose
    private Integer openIssues;
    @SerializedName("watchers")
    @Expose
    private Integer watchers;
    @SerializedName("default_branch")
    @Expose
    private String defaultBranch;
    @SerializedName("permissions")
    @Expose
    private Permissions permissions;

    public UserRepoJson(){

    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     *
     * @param fullName
     * The full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     *
     * @return
     * The owner
     */
    public Owner getOwner() {
        return owner;
    }

    /**
     *
     * @param owner
     * The owner
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     *
     * @return
     * The _private
     */
    public Boolean getPrivate() {
        return _private;
    }

    /**
     *
     * @param _private
     * The private
     */
    public void setPrivate(Boolean _private) {
        this._private = _private;
    }

    /**
     *
     * @return
     * The htmlUrl
     */
    public String getHtmlUrl() {
        return htmlUrl;
    }

    /**
     *
     * @param htmlUrl
     * The html_url
     */
    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The fork
     */
    public Boolean getFork() {
        return fork;
    }

    /**
     *
     * @param fork
     * The fork
     */
    public void setFork(Boolean fork) {
        this.fork = fork;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     * The forksUrl
     */
    public String getForksUrl() {
        return forksUrl;
    }

    /**
     *
     * @param forksUrl
     * The forks_url
     */
    public void setForksUrl(String forksUrl) {
        this.forksUrl = forksUrl;
    }

    /**
     *
     * @return
     * The keysUrl
     */
    public String getKeysUrl() {
        return keysUrl;
    }

    /**
     *
     * @param keysUrl
     * The keys_url
     */
    public void setKeysUrl(String keysUrl) {
        this.keysUrl = keysUrl;
    }

    /**
     *
     * @return
     * The collaboratorsUrl
     */
    public String getCollaboratorsUrl() {
        return collaboratorsUrl;
    }

    /**
     *
     * @param collaboratorsUrl
     * The collaborators_url
     */
    public void setCollaboratorsUrl(String collaboratorsUrl) {
        this.collaboratorsUrl = collaboratorsUrl;
    }

    /**
     *
     * @return
     * The teamsUrl
     */
    public String getTeamsUrl() {
        return teamsUrl;
    }

    /**
     *
     * @param teamsUrl
     * The teams_url
     */
    public void setTeamsUrl(String teamsUrl) {
        this.teamsUrl = teamsUrl;
    }

    /**
     *
     * @return
     * The hooksUrl
     */
    public String getHooksUrl() {
        return hooksUrl;
    }

    /**
     *
     * @param hooksUrl
     * The hooks_url
     */
    public void setHooksUrl(String hooksUrl) {
        this.hooksUrl = hooksUrl;
    }

    /**
     *
     * @return
     * The issueEventsUrl
     */
    public String getIssueEventsUrl() {
        return issueEventsUrl;
    }

    /**
     *
     * @param issueEventsUrl
     * The issue_events_url
     */
    public void setIssueEventsUrl(String issueEventsUrl) {
        this.issueEventsUrl = issueEventsUrl;
    }

    /**
     *
     * @return
     * The eventsUrl
     */
    public String getEventsUrl() {
        return eventsUrl;
    }

    /**
     *
     * @param eventsUrl
     * The events_url
     */
    public void setEventsUrl(String eventsUrl) {
        this.eventsUrl = eventsUrl;
    }

    /**
     *
     * @return
     * The assigneesUrl
     */
    public String getAssigneesUrl() {
        return assigneesUrl;
    }

    /**
     *
     * @param assigneesUrl
     * The assignees_url
     */
    public void setAssigneesUrl(String assigneesUrl) {
        this.assigneesUrl = assigneesUrl;
    }

    /**
     *
     * @return
     * The branchesUrl
     */
    public String getBranchesUrl() {
        return branchesUrl;
    }

    /**
     *
     * @param branchesUrl
     * The branches_url
     */
    public void setBranchesUrl(String branchesUrl) {
        this.branchesUrl = branchesUrl;
    }

    /**
     *
     * @return
     * The tagsUrl
     */
    public String getTagsUrl() {
        return tagsUrl;
    }

    /**
     *
     * @param tagsUrl
     * The tags_url
     */
    public void setTagsUrl(String tagsUrl) {
        this.tagsUrl = tagsUrl;
    }

    /**
     *
     * @return
     * The blobsUrl
     */
    public String getBlobsUrl() {
        return blobsUrl;
    }

    /**
     *
     * @param blobsUrl
     * The blobs_url
     */
    public void setBlobsUrl(String blobsUrl) {
        this.blobsUrl = blobsUrl;
    }

    /**
     *
     * @return
     * The gitTagsUrl
     */
    public String getGitTagsUrl() {
        return gitTagsUrl;
    }

    /**
     *
     * @param gitTagsUrl
     * The git_tags_url
     */
    public void setGitTagsUrl(String gitTagsUrl) {
        this.gitTagsUrl = gitTagsUrl;
    }

    /**
     *
     * @return
     * The gitRefsUrl
     */
    public String getGitRefsUrl() {
        return gitRefsUrl;
    }

    /**
     *
     * @param gitRefsUrl
     * The git_refs_url
     */
    public void setGitRefsUrl(String gitRefsUrl) {
        this.gitRefsUrl = gitRefsUrl;
    }

    /**
     *
     * @return
     * The treesUrl
     */
    public String getTreesUrl() {
        return treesUrl;
    }

    /**
     *
     * @param treesUrl
     * The trees_url
     */
    public void setTreesUrl(String treesUrl) {
        this.treesUrl = treesUrl;
    }

    /**
     *
     * @return
     * The statusesUrl
     */
    public String getStatusesUrl() {
        return statusesUrl;
    }

    /**
     *
     * @param statusesUrl
     * The statuses_url
     */
    public void setStatusesUrl(String statusesUrl) {
        this.statusesUrl = statusesUrl;
    }

    /**
     *
     * @return
     * The languagesUrl
     */
    public String getLanguagesUrl() {
        return languagesUrl;
    }

    /**
     *
     * @param languagesUrl
     * The languages_url
     */
    public void setLanguagesUrl(String languagesUrl) {
        this.languagesUrl = languagesUrl;
    }

    /**
     *
     * @return
     * The stargazersUrl
     */
    public String getStargazersUrl() {
        return stargazersUrl;
    }

    /**
     *
     * @param stargazersUrl
     * The stargazers_url
     */
    public void setStargazersUrl(String stargazersUrl) {
        this.stargazersUrl = stargazersUrl;
    }

    /**
     *
     * @return
     * The contributorsUrl
     */
    public String getContributorsUrl() {
        return contributorsUrl;
    }

    /**
     *
     * @param contributorsUrl
     * The contributors_url
     */
    public void setContributorsUrl(String contributorsUrl) {
        this.contributorsUrl = contributorsUrl;
    }

    /**
     *
     * @return
     * The subscribersUrl
     */
    public String getSubscribersUrl() {
        return subscribersUrl;
    }

    /**
     *
     * @param subscribersUrl
     * The subscribers_url
     */
    public void setSubscribersUrl(String subscribersUrl) {
        this.subscribersUrl = subscribersUrl;
    }

    /**
     *
     * @return
     * The subscriptionUrl
     */
    public String getSubscriptionUrl() {
        return subscriptionUrl;
    }

    /**
     *
     * @param subscriptionUrl
     * The subscription_url
     */
    public void setSubscriptionUrl(String subscriptionUrl) {
        this.subscriptionUrl = subscriptionUrl;
    }

    /**
     *
     * @return
     * The commitsUrl
     */
    public String getCommitsUrl() {
        return commitsUrl;
    }

    /**
     *
     * @param commitsUrl
     * The commits_url
     */
    public void setCommitsUrl(String commitsUrl) {
        this.commitsUrl = commitsUrl;
    }

    /**
     *
     * @return
     * The gitCommitsUrl
     */
    public String getGitCommitsUrl() {
        return gitCommitsUrl;
    }

    /**
     *
     * @param gitCommitsUrl
     * The git_commits_url
     */
    public void setGitCommitsUrl(String gitCommitsUrl) {
        this.gitCommitsUrl = gitCommitsUrl;
    }

    /**
     *
     * @return
     * The commentsUrl
     */
    public String getCommentsUrl() {
        return commentsUrl;
    }

    /**
     *
     * @param commentsUrl
     * The comments_url
     */
    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    /**
     *
     * @return
     * The issueCommentUrl
     */
    public String getIssueCommentUrl() {
        return issueCommentUrl;
    }

    /**
     *
     * @param issueCommentUrl
     * The issue_comment_url
     */
    public void setIssueCommentUrl(String issueCommentUrl) {
        this.issueCommentUrl = issueCommentUrl;
    }

    /**
     *
     * @return
     * The contentsUrl
     */
    public String getContentsUrl() {
        return contentsUrl;
    }

    /**
     *
     * @param contentsUrl
     * The contents_url
     */
    public void setContentsUrl(String contentsUrl) {
        this.contentsUrl = contentsUrl;
    }

    /**
     *
     * @return
     * The compareUrl
     */
    public String getCompareUrl() {
        return compareUrl;
    }

    /**
     *
     * @param compareUrl
     * The compare_url
     */
    public void setCompareUrl(String compareUrl) {
        this.compareUrl = compareUrl;
    }

    /**
     *
     * @return
     * The mergesUrl
     */
    public String getMergesUrl() {
        return mergesUrl;
    }

    /**
     *
     * @param mergesUrl
     * The merges_url
     */
    public void setMergesUrl(String mergesUrl) {
        this.mergesUrl = mergesUrl;
    }

    /**
     *
     * @return
     * The archiveUrl
     */
    public String getArchiveUrl() {
        return archiveUrl;
    }

    /**
     *
     * @param archiveUrl
     * The archive_url
     */
    public void setArchiveUrl(String archiveUrl) {
        this.archiveUrl = archiveUrl;
    }

    /**
     *
     * @return
     * The downloadsUrl
     */
    public String getDownloadsUrl() {
        return downloadsUrl;
    }

    /**
     *
     * @param downloadsUrl
     * The downloads_url
     */
    public void setDownloadsUrl(String downloadsUrl) {
        this.downloadsUrl = downloadsUrl;
    }

    /**
     *
     * @return
     * The issuesUrl
     */
    public String getIssuesUrl() {
        return issuesUrl;
    }

    /**
     *
     * @param issuesUrl
     * The issues_url
     */
    public void setIssuesUrl(String issuesUrl) {
        this.issuesUrl = issuesUrl;
    }

    /**
     *
     * @return
     * The pullsUrl
     */
    public String getPullsUrl() {
        return pullsUrl;
    }

    /**
     *
     * @param pullsUrl
     * The pulls_url
     */
    public void setPullsUrl(String pullsUrl) {
        this.pullsUrl = pullsUrl;
    }

    /**
     *
     * @return
     * The milestonesUrl
     */
    public String getMilestonesUrl() {
        return milestonesUrl;
    }

    /**
     *
     * @param milestonesUrl
     * The milestones_url
     */
    public void setMilestonesUrl(String milestonesUrl) {
        this.milestonesUrl = milestonesUrl;
    }

    /**
     *
     * @return
     * The notificationsUrl
     */
    public String getNotificationsUrl() {
        return notificationsUrl;
    }

    /**
     *
     * @param notificationsUrl
     * The notifications_url
     */
    public void setNotificationsUrl(String notificationsUrl) {
        this.notificationsUrl = notificationsUrl;
    }

    /**
     *
     * @return
     * The labelsUrl
     */
    public String getLabelsUrl() {
        return labelsUrl;
    }

    /**
     *
     * @param labelsUrl
     * The labels_url
     */
    public void setLabelsUrl(String labelsUrl) {
        this.labelsUrl = labelsUrl;
    }

    /**
     *
     * @return
     * The releasesUrl
     */
    public String getReleasesUrl() {
        return releasesUrl;
    }

    /**
     *
     * @param releasesUrl
     * The releases_url
     */
    public void setReleasesUrl(String releasesUrl) {
        this.releasesUrl = releasesUrl;
    }

    /**
     *
     * @return
     * The deploymentsUrl
     */
    public String getDeploymentsUrl() {
        return deploymentsUrl;
    }

    /**
     *
     * @param deploymentsUrl
     * The deployments_url
     */
    public void setDeploymentsUrl(String deploymentsUrl) {
        this.deploymentsUrl = deploymentsUrl;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     *
     * @return
     * The pushedAt
     */
    public String getPushedAt() {
        return pushedAt;
    }

    /**
     *
     * @param pushedAt
     * The pushed_at
     */
    public void setPushedAt(String pushedAt) {
        this.pushedAt = pushedAt;
    }

    /**
     *
     * @return
     * The gitUrl
     */
    public String getGitUrl() {
        return gitUrl;
    }

    /**
     *
     * @param gitUrl
     * The git_url
     */
    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }

    /**
     *
     * @return
     * The sshUrl
     */
    public String getSshUrl() {
        return sshUrl;
    }

    /**
     *
     * @param sshUrl
     * The ssh_url
     */
    public void setSshUrl(String sshUrl) {
        this.sshUrl = sshUrl;
    }

    /**
     *
     * @return
     * The cloneUrl
     */
    public String getCloneUrl() {
        return cloneUrl;
    }

    /**
     *
     * @param cloneUrl
     * The clone_url
     */
    public void setCloneUrl(String cloneUrl) {
        this.cloneUrl = cloneUrl;
    }

    /**
     *
     * @return
     * The svnUrl
     */
    public String getSvnUrl() {
        return svnUrl;
    }

    /**
     *
     * @param svnUrl
     * The svn_url
     */
    public void setSvnUrl(String svnUrl) {
        this.svnUrl = svnUrl;
    }

    /**
     *
     * @return
     * The homepage
     */
    public Object getHomepage() {
        return homepage;
    }

    /**
     *
     * @param homepage
     * The homepage
     */
    public void setHomepage(Object homepage) {
        this.homepage = homepage;
    }

    /**
     *
     * @return
     * The size
     */
    public Integer getSize() {
        return size;
    }

    /**
     *
     * @param size
     * The size
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     *
     * @return
     * The stargazersCount
     */
    public Integer getStargazersCount() {
        return stargazersCount;
    }

    /**
     *
     * @param stargazersCount
     * The stargazers_count
     */
    public void setStargazersCount(Integer stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    /**
     *
     * @return
     * The watchersCount
     */
    public Integer getWatchersCount() {
        return watchersCount;
    }

    /**
     *
     * @param watchersCount
     * The watchers_count
     */
    public void setWatchersCount(Integer watchersCount) {
        this.watchersCount = watchersCount;
    }

    /**
     *
     * @return
     * The language
     */
    public String getLanguage() {
        return language;
    }

    /**
     *
     * @param language
     * The language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     *
     * @return
     * The hasIssues
     */
    public Boolean getHasIssues() {
        return hasIssues;
    }

    /**
     *
     * @param hasIssues
     * The has_issues
     */
    public void setHasIssues(Boolean hasIssues) {
        this.hasIssues = hasIssues;
    }

    /**
     *
     * @return
     * The hasDownloads
     */
    public Boolean getHasDownloads() {
        return hasDownloads;
    }

    /**
     *
     * @param hasDownloads
     * The has_downloads
     */
    public void setHasDownloads(Boolean hasDownloads) {
        this.hasDownloads = hasDownloads;
    }

    /**
     *
     * @return
     * The hasWiki
     */
    public Boolean getHasWiki() {
        return hasWiki;
    }

    /**
     *
     * @param hasWiki
     * The has_wiki
     */
    public void setHasWiki(Boolean hasWiki) {
        this.hasWiki = hasWiki;
    }

    /**
     *
     * @return
     * The hasPages
     */
    public Boolean getHasPages() {
        return hasPages;
    }

    /**
     *
     * @param hasPages
     * The has_pages
     */
    public void setHasPages(Boolean hasPages) {
        this.hasPages = hasPages;
    }

    /**
     *
     * @return
     * The forksCount
     */
    public Integer getForksCount() {
        return forksCount;
    }

    /**
     *
     * @param forksCount
     * The forks_count
     */
    public void setForksCount(Integer forksCount) {
        this.forksCount = forksCount;
    }

    /**
     *
     * @return
     * The mirrorUrl
     */
    public Object getMirrorUrl() {
        return mirrorUrl;
    }

    /**
     *
     * @param mirrorUrl
     * The mirror_url
     */
    public void setMirrorUrl(Object mirrorUrl) {
        this.mirrorUrl = mirrorUrl;
    }

    /**
     *
     * @return
     * The openIssuesCount
     */
    public Integer getOpenIssuesCount() {
        return openIssuesCount;
    }

    /**
     *
     * @param openIssuesCount
     * The open_issues_count
     */
    public void setOpenIssuesCount(Integer openIssuesCount) {
        this.openIssuesCount = openIssuesCount;
    }

    /**
     *
     * @return
     * The forks
     */
    public Integer getForks() {
        return forks;
    }

    /**
     *
     * @param forks
     * The forks
     */
    public void setForks(Integer forks) {
        this.forks = forks;
    }

    /**
     *
     * @return
     * The openIssues
     */
    public Integer getOpenIssues() {
        return openIssues;
    }

    /**
     *
     * @param openIssues
     * The open_issues
     */
    public void setOpenIssues(Integer openIssues) {
        this.openIssues = openIssues;
    }

    /**
     *
     * @return
     * The watchers
     */
    public Integer getWatchers() {
        return watchers;
    }

    /**
     *
     * @param watchers
     * The watchers
     */
    public void setWatchers(Integer watchers) {
        this.watchers = watchers;
    }

    /**
     *
     * @return
     * The defaultBranch
     */
    public String getDefaultBranch() {
        return defaultBranch;
    }

    /**
     *
     * @param defaultBranch
     * The default_branch
     */
    public void setDefaultBranch(String defaultBranch) {
        this.defaultBranch = defaultBranch;
    }

    /**
     *
     * @return
     * The permissions
     */
    public Permissions getPermissions() {
        return permissions;
    }

    /**
     *
     * @param permissions
     * The permissions
     */
    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }


    protected UserRepoJson(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        name = in.readString();
        fullName = in.readString();
        owner = (Owner) in.readValue(Owner.class.getClassLoader());
        byte _privateVal = in.readByte();
        _private = _privateVal == 0x02 ? null : _privateVal != 0x00;
        htmlUrl = in.readString();
        description = in.readString();
        byte forkVal = in.readByte();
        fork = forkVal == 0x02 ? null : forkVal != 0x00;
        url = in.readString();
        forksUrl = in.readString();
        keysUrl = in.readString();
        collaboratorsUrl = in.readString();
        teamsUrl = in.readString();
        hooksUrl = in.readString();
        issueEventsUrl = in.readString();
        eventsUrl = in.readString();
        assigneesUrl = in.readString();
        branchesUrl = in.readString();
        tagsUrl = in.readString();
        blobsUrl = in.readString();
        gitTagsUrl = in.readString();
        gitRefsUrl = in.readString();
        treesUrl = in.readString();
        statusesUrl = in.readString();
        languagesUrl = in.readString();
        stargazersUrl = in.readString();
        contributorsUrl = in.readString();
        subscribersUrl = in.readString();
        subscriptionUrl = in.readString();
        commitsUrl = in.readString();
        gitCommitsUrl = in.readString();
        commentsUrl = in.readString();
        issueCommentUrl = in.readString();
        contentsUrl = in.readString();
        compareUrl = in.readString();
        mergesUrl = in.readString();
        archiveUrl = in.readString();
        downloadsUrl = in.readString();
        issuesUrl = in.readString();
        pullsUrl = in.readString();
        milestonesUrl = in.readString();
        notificationsUrl = in.readString();
        labelsUrl = in.readString();
        releasesUrl = in.readString();
        deploymentsUrl = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        pushedAt = in.readString();
        gitUrl = in.readString();
        sshUrl = in.readString();
        cloneUrl = in.readString();
        svnUrl = in.readString();
        homepage = (Object) in.readValue(Object.class.getClassLoader());
        size = in.readByte() == 0x00 ? null : in.readInt();
        stargazersCount = in.readByte() == 0x00 ? null : in.readInt();
        watchersCount = in.readByte() == 0x00 ? null : in.readInt();
        language = in.readString();
        byte hasIssuesVal = in.readByte();
        hasIssues = hasIssuesVal == 0x02 ? null : hasIssuesVal != 0x00;
        byte hasDownloadsVal = in.readByte();
        hasDownloads = hasDownloadsVal == 0x02 ? null : hasDownloadsVal != 0x00;
        byte hasWikiVal = in.readByte();
        hasWiki = hasWikiVal == 0x02 ? null : hasWikiVal != 0x00;
        byte hasPagesVal = in.readByte();
        hasPages = hasPagesVal == 0x02 ? null : hasPagesVal != 0x00;
        forksCount = in.readByte() == 0x00 ? null : in.readInt();
        mirrorUrl = (Object) in.readValue(Object.class.getClassLoader());
        openIssuesCount = in.readByte() == 0x00 ? null : in.readInt();
        forks = in.readByte() == 0x00 ? null : in.readInt();
        openIssues = in.readByte() == 0x00 ? null : in.readInt();
        watchers = in.readByte() == 0x00 ? null : in.readInt();
        defaultBranch = in.readString();
        permissions = (Permissions) in.readValue(Permissions.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(fullName);
        dest.writeValue(owner);
        if (_private == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (_private ? 0x01 : 0x00));
        }
        dest.writeString(htmlUrl);
        dest.writeString(description);
        if (fork == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (fork ? 0x01 : 0x00));
        }
        dest.writeString(url);
        dest.writeString(forksUrl);
        dest.writeString(keysUrl);
        dest.writeString(collaboratorsUrl);
        dest.writeString(teamsUrl);
        dest.writeString(hooksUrl);
        dest.writeString(issueEventsUrl);
        dest.writeString(eventsUrl);
        dest.writeString(assigneesUrl);
        dest.writeString(branchesUrl);
        dest.writeString(tagsUrl);
        dest.writeString(blobsUrl);
        dest.writeString(gitTagsUrl);
        dest.writeString(gitRefsUrl);
        dest.writeString(treesUrl);
        dest.writeString(statusesUrl);
        dest.writeString(languagesUrl);
        dest.writeString(stargazersUrl);
        dest.writeString(contributorsUrl);
        dest.writeString(subscribersUrl);
        dest.writeString(subscriptionUrl);
        dest.writeString(commitsUrl);
        dest.writeString(gitCommitsUrl);
        dest.writeString(commentsUrl);
        dest.writeString(issueCommentUrl);
        dest.writeString(contentsUrl);
        dest.writeString(compareUrl);
        dest.writeString(mergesUrl);
        dest.writeString(archiveUrl);
        dest.writeString(downloadsUrl);
        dest.writeString(issuesUrl);
        dest.writeString(pullsUrl);
        dest.writeString(milestonesUrl);
        dest.writeString(notificationsUrl);
        dest.writeString(labelsUrl);
        dest.writeString(releasesUrl);
        dest.writeString(deploymentsUrl);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeString(pushedAt);
        dest.writeString(gitUrl);
        dest.writeString(sshUrl);
        dest.writeString(cloneUrl);
        dest.writeString(svnUrl);
        dest.writeValue(homepage);
        if (size == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(size);
        }
        if (stargazersCount == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(stargazersCount);
        }
        if (watchersCount == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(watchersCount);
        }
        dest.writeString(language);
        if (hasIssues == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (hasIssues ? 0x01 : 0x00));
        }
        if (hasDownloads == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (hasDownloads ? 0x01 : 0x00));
        }
        if (hasWiki == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (hasWiki ? 0x01 : 0x00));
        }
        if (hasPages == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (hasPages ? 0x01 : 0x00));
        }
        if (forksCount == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(forksCount);
        }
        dest.writeValue(mirrorUrl);
        if (openIssuesCount == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(openIssuesCount);
        }
        if (forks == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(forks);
        }
        if (openIssues == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(openIssues);
        }
        if (watchers == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(watchers);
        }
        dest.writeString(defaultBranch);
        dest.writeValue(permissions);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<UserRepoJson> CREATOR = new Parcelable.Creator<UserRepoJson>() {
        @Override
        public UserRepoJson createFromParcel(Parcel in) {
            return new UserRepoJson(in);
        }

        @Override
        public UserRepoJson[] newArray(int size) {
            return new UserRepoJson[size];
        }
    };
}