package codin.msbackendcore.iam.application.internal.domainservice;

import codin.msbackendcore.iam.domain.model.entities.AuditLog;
import codin.msbackendcore.iam.domain.services.AuditLogDomainService;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class AuditLogDomainServiceImpl implements AuditLogDomainService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogDomainServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public AuditLog recordAction(
            UUID tenantId,
            UUID actorUserId,
            UUID targetUserId,
            String action,
            Map<String, Object> metadata
    ) {
        AuditLog audit = new AuditLog();
        audit.setTenantId(tenantId);
        audit.setActorUserId(actorUserId);
        audit.setTargetUserId(targetUserId);
        audit.setAction(action);
        audit.setMetadata(metadata);
        return auditLogRepository.save(audit);
    }
}
