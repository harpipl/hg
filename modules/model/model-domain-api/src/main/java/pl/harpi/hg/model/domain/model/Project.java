package pl.harpi.hg.model.domain.model;

import lombok.Builder;

import java.util.Hashtable;
import java.util.List;

@Builder
public record Project(
    String modelVersion,
    Parent parent,
    String groupId,
    String artifactId,
    String version,
    String packaging,
    String name,
    String description,
    String url,
    String inceptionYear,
    Organization organization,
    List<License> licenses,
    List<Developer> developers,
    List<Contributor> contributors,
    List<MailingList> mailingLists,
    Prerequisites prerequisites,
    List<String> modules,
    Scm scm,
    IssueManagement issueManagement,
    CiManagement ciManagement,
    DistributionManagement distributionManagement,
    Hashtable<Object, Object> properties,
    DependencyManagement dependencyManagement,
    List<Dependency> dependencies,
    List<Repository> repositories,
    List<Repository> pluginRepositories,
    Build build,
    Object reports,
    Reporting reporting,
    List<Profile> profiles
    ) {}
