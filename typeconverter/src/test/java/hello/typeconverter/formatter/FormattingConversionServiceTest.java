package hello.typeconverter.formatter;

import hello.typeconverter.converter.IpPortToStringConverter;
import hello.typeconverter.converter.StringToIpPortConverter;
import hello.typeconverter.type.IpPort;
import org.junit.jupiter.api.Test;
import org.springframework.format.support.DefaultFormattingConversionService;

import static org.assertj.core.api.Assertions.assertThat;

public class FormattingConversionServiceTest {

    @Test
    void formattingConversionService(){
        DefaultFormattingConversionService formattingConversionService = new DefaultFormattingConversionService();

        formattingConversionService.addConverter(new StringToIpPortConverter());
        formattingConversionService.addConverter(new IpPortToStringConverter());
        formattingConversionService.addFormatter(new MyNumberFormatter());

        IpPort ipPort = formattingConversionService.convert("127.0.0.1:8080", IpPort.class);
        assertThat(ipPort).isEqualTo(new IpPort("127.0.0.1", 8080));
        assertThat(formattingConversionService.convert(1000, String.class)).isEqualTo("1,000");
        assertThat(formattingConversionService.convert("1,000", Long.class)).isEqualTo(1000L);
    }
}
