package innowise.zuevsky.helpdesk.mapper;

import innowise.zuevsky.helpdesk.dto.FilterParamsDto;
import innowise.zuevsky.helpdesk.util.FilterParamsUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FilterParamsMapperTest {

    private final FilterParamsMapper filterParamsMapper = new FilterParamsMapper();

    @Test
    void mapParamsInFilterParamsDto_ShouldPass_IfNumberOfFieldsIsRight() {
        FilterParamsDto filterParamsDto = FilterParamsUtil.createFilterParamsDto();

        assertThat(filterParamsDto)
                .usingRecursiveComparison()
                .isEqualTo(filterParamsMapper.mapParamsInFilterParamsDto(
                        FilterParamsUtil.ID, FilterParamsUtil.NAME, FilterParamsUtil.DESIRED_DATE, FilterParamsUtil.URGENCIES, FilterParamsUtil.STATES));
    }
}