package innowise.helpdesk.attachments.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class YandexDiskInfoDto {

    @JsonProperty("total_space")
    private String totalSpace;

    @JsonProperty("used_space")
    private String usedSpace;
}

