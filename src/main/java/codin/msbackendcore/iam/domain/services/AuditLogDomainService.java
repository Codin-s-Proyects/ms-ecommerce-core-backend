package codin.msbackendcore.iam.domain.services;

import codin.msbackendcore.iam.domain.model.entities.AuditLog;

import java.util.Map;
import java.util.UUID;

public interface AuditLogDomainService {

    AuditLog recordAction(
            UUID tenantId,
            UUID actorUserId,
            UUID targetUserId,
            String action,
            Map<String, Object> metadata
    );
}