package innowise.helpdesk.attachments.service;

import innowise.helpdesk.attachments.dto.YandexDiskInfoDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FileService {

    public ResponseEntity<YandexDiskInfoDto> getYandexDiskInfo(String oauth) {
        String uri = "https://cloud-api.yandex.net/v1/disk";
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", oauth);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        return template.exchange(uri, HttpMethod.GET, httpEntity, YandexDiskInfoDto.class);
    }

    public String saveFileInYandexDisk(String oauth, String path) {
        String uri = "https://cloud-api.yandex.net/v1/disk";
        return null;
    }
}

