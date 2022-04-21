package innowise.zuevsky.helpdesk.mapper;

import innowise.zuevsky.helpdesk.dto.FilterParamsDto;
import innowise.zuevsky.helpdesk.util.FilterParamsTestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class    FilterParamsMapperTest {

    private final FilterParamsMapper filterParamsMapper = new FilterParamsMapper();

    @Test
    void mapParamsInFilterParamsDto_ShouldPass_IfNumberOfFieldsIsRight() {
        FilterParamsDto filterParamsDto = FilterParamsTestUtil.createFilterParamsDto();

        assertThat(filterParamsDto)
                .usingRecursiveComparison()
                .isEqualTo(filterParamsMapper.mapParamsInFilterParamsDto(
                        FilterParamsTestUtil.ID, FilterParamsTestUtil.NAME, FilterParamsTestUtil.DESIRED_DATE, FilterParamsTestUtil.URGENCIES, FilterParamsTestUtil.STATES));
    }
}