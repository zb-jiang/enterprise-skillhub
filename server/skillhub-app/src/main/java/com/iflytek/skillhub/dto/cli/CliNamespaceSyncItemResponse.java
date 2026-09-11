package com.iflytek.skillhub.dto.cli;

import java.time.Instant;

/**
 * Installable skill metadata used by the CLI namespace workspace synchronizer.
 *
 * @param summary skill 描述(SKILL.md frontmatter description,发布时落库到 skill.summary)
 */
public record CliNamespaceSyncItemResponse(
        String namespace,
        String slug,
        String summary,
        String version,
        Long versionId,
        String fingerprint,
        Instant updatedAt,
        String visibility,
        String downloadUrl
) {}
