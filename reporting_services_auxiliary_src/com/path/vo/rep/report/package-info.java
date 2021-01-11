@XmlJavaTypeAdapters({
	@XmlJavaTypeAdapter(value=BigDecimalAdapter.class, type=java.math.BigDecimal.class)
})
package com.path.vo.rep.report;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import com.path.lib.common.util.BigDecimalAdapter;
