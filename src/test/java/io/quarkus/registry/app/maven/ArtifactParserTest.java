package io.quarkus.registry.app.maven;

import java.util.Arrays;
import java.util.List;


import io.quarkus.maven.ArtifactCoords;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ArtifactParserTest {


    @Test
    public void testClassifier() {
        List<String> pathSegments = toSegments("io/quarkus/registry/quarkus-non-platform-extensions/1.0-SNAPSHOT/quarkus-non-platform-extensions-1.0-SNAPSHOT-1.13.0.Final.json");
        ArtifactCoords artifact = ArtifactParser.parseCoords(pathSegments);
        assertThat(artifact.getGroupId()).withFailMessage("Group ID does not match")
                .isEqualTo("io.quarkus.registry");
        assertThat(artifact.getArtifactId()).withFailMessage("Artifact ID does not match")
                .isEqualTo("quarkus-non-platform-extensions");
        assertThat(artifact.getVersion()).withFailMessage("Version does not match")
                .isEqualTo("1.0-SNAPSHOT");
        assertThat(artifact.getType()).withFailMessage("Type does not match")
                .isEqualTo("json");
        assertThat(artifact.getClassifier())
                .isEqualTo("1.13.0.Final");
    }

    @Test
    public void testSha1() {
        List<String> pathSegments = toSegments("io/quarkus/registry/quarkus-non-platform-extensions/1.0-SNAPSHOT/quarkus-non-platform-extensions-1.0-SNAPSHOT-1.13.0.Final.json.sha1");
        ArtifactCoords artifact = ArtifactParser.parseCoords(pathSegments);
        assertThat(artifact.getGroupId()).isEqualTo("io.quarkus.registry");
        assertThat(artifact.getArtifactId()).isEqualTo("quarkus-non-platform-extensions");
        assertThat(artifact.getVersion()).isEqualTo("1.0-SNAPSHOT");
        assertThat(artifact.getType()).isEqualTo("json");
        assertThat(artifact.getClassifier()).isEqualTo("1.13.0.Final");
    }

    @Test
    @Disabled
    public void testMavenMetadata() {
        List<String> pathSegments = toSegments("io/quarkus/registry/quarkus-non-platform-extensions/maven-metadata.xml");
        ArtifactCoords artifact = ArtifactParser.parseCoords(pathSegments);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(artifact.getGroupId()).isEqualTo("io.quarkus.registry");
        softly.assertThat(artifact.getArtifactId()).isEqualTo("quarkus-non-platform-extensions");
        softly.assertThat(artifact.getVersion()).isEqualTo(MavenConfig.VERSION);
        softly.assertThat(artifact.getType()).isEqualTo("maven-metadata.xml");
        softly.assertAll();
    }

    @Test
    public void testVersionedMavenMetadata() {
        List<String> pathSegments = toSegments("io/quarkus/registry/quarkus-non-platform-extensions/1.0-SNAPSHOT/maven-metadata.xml");
        ArtifactCoords artifact = ArtifactParser.parseCoords(pathSegments);
        assertThat(artifact.getGroupId()).isEqualTo("io.quarkus.registry");
        assertThat(artifact.getArtifactId()).isEqualTo("quarkus-non-platform-extensions");
        assertThat(artifact.getVersion()).isEqualTo("1.0-SNAPSHOT");
        assertThat(artifact.getType()).isEqualTo("maven-metadata.xml");
    }

    @Test
    public void testVersionedSnapshotMavenMetadata() {
        List<String> pathSegments = toSegments("io/quarkus/registry/quarkus-registry-descriptor/1.0-SNAPSHOT/quarkus-registry-descriptor-1.0-20210331.162601-1.json");
        ArtifactCoords artifact = ArtifactParser.parseCoords(pathSegments);
        assertThat(artifact.getGroupId()).isEqualTo("io.quarkus.registry");
        assertThat(artifact.getArtifactId()).isEqualTo("quarkus-registry-descriptor");
        assertThat(artifact.getVersion()).isEqualTo("1.0-SNAPSHOT");
        assertThat(artifact.getClassifier()).isEmpty();
        assertThat(artifact.getType()).isEqualTo("json");
    }

    @Test
    public void testVersionedSnapshotMavenMetadataWithClassifier() {
        List<String> pathSegments = toSegments("io/quarkus/registry/quarkus-non-platform-extensions/1.0-SNAPSHOT/quarkus-non-platform-extensions-1.0-20210405.152106-1-1.13.0.Final.json");
        ArtifactCoords artifact = ArtifactParser.parseCoords(pathSegments);
        assertThat(artifact.getGroupId()).isEqualTo("io.quarkus.registry");
        assertThat(artifact.getArtifactId()).isEqualTo("quarkus-non-platform-extensions");
        assertThat(artifact.getVersion()).isEqualTo("1.0-SNAPSHOT");
        assertThat(artifact.getClassifier()).isEqualTo("1.13.0.Final");
        assertThat(artifact.getType()).isEqualTo("json");
    }

    private List<String> toSegments(String s) {
        return Arrays.asList(s.split("/"));
    }

}