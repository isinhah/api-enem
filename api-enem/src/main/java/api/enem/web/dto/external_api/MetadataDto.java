package api.enem.web.dto.external_api;

public record MetadataDto(int limit, int offset, int total, boolean hasMore) {
}
