package dev.spider.entity.mapper;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BeanMapper {

    @Mapper
    public interface DecimalMapper {
        DecimalMapper instance = Mappers.getMapper(DecimalMapper.class);

        @Mapping(source = "amt", target = "amount")
        @Mapping(source = "createTime", target = "time")
        Bar convert(Foo f);
    }
    //       if (f == null) {
    //            return null;
    //        } else {
    //            Bar bar = new Bar();
    //            if (f.getAmt() != null) {
    //                bar.setAmount(new BigDecimal(f.getAmt()));
    //            }
    //
    //            if (f.getCreateTime() != null) {
    //                bar.setTime((new SimpleDateFormat()).format(f.getCreateTime()));
    //            }
    //
    //            bar.setVal(f.getVal());
    //            return bar;
    //        }

    public static void main(String[] args) {
        Foo foo = new Foo();
        foo.setVal("map struct");
        foo.setAmt("5.23");
        foo.setCreateTime(new Date());
        Bar bar = DecimalMapper.instance.convert(foo);
        System.out.println(bar);
        System.out.println(bar.getAmount());
    }
}

@Data
class Foo implements Serializable {
    String val;
    String amt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Date createTime;
}

@Data
class Bar implements Serializable {
    String val;
    BigDecimal amount;
    String time;
}

