package zw.co.zimra.customspayments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
    private int pageNumber;
    private int pageSize;
    private SortData sort;
    private int offset;
    private boolean paged;
    private boolean unpaged;

}
