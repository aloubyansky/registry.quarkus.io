package io.quarkus.registry.app.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.runtime.JpaOperations;

/**
 * Quarkus core releases
 */
@Entity
@NamedQuery(name = "CoreRelease.findAllVersions", query = "SELECT r.version FROM CoreRelease r ORDER BY r.createdAt DESC")
@Table(indexes = { @Index(name = "CoreRelease_NaturalId", columnList = "groupId,artifactId,version", unique = true) })
public class CoreRelease extends BaseEntity {

    @Column(nullable = false)
    public String groupId;

    @Column(nullable = false)
    public String artifactId;

    @Column(nullable = false)
    public String version;

    @ManyToOne
    public CoreRelease majorRelease;

    @OneToMany(mappedBy = "majorRelease")
    public List<CoreRelease> minorReleases;

    @ManyToMany
    public List<Extension> compatibleExtensions;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CoreRelease)) {
            return false;
        }
        CoreRelease that = (CoreRelease) o;
        return Objects.equals(version, that.version);
    }

    @Override public String toString() {
        return "CoreRelease{" +
                "version='" + version + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(version);
    }

    public boolean isPreRelease() {
        return !version.endsWith("Final");
    }

    public static List<String> findAllVersions() {
        return JpaOperations.getEntityManager()
                .createNamedQuery("CoreRelease.findAllVersions", String.class)
                .getResultList();
    }
}
