package dev.spider.entity.mapper;

import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.io.Serializable;
import java.math.BigDecimal;

public class BeanMapper {

    @Mapper
    public interface DecimalMapper {
        DecimalMapper instance = Mappers.getMapper(DecimalMapper.class);

        @Mapping(source = "amt", target = "amount")
        Bar convert(Foo f);
    }
    // public class BeanMapper$DecimalMapperImpl implements DecimalMapper {
    //    public BeanMapper$DecimalMapperImpl() {
    //    }
    //
    //    public Bar convert(Foo f) {
    //        if (f == null) {
    //            return null;
    //        } else {
    //            Bar bar = new Bar();
    //            if (f.getAmt() != null) {
    //                bar.setAmount(new BigDecimal(f.getAmt()));
    //            }
    //
    //            bar.setVal(f.getVal());
    //            return bar;
    //        }
    //    }
    //}

    public static void main(String[] args) {
        Foo foo = new Foo();
        foo.setVal("map struct");
        foo.setAmt("5.23");
        Bar bar = DecimalMapper.instance.convert(foo);
        System.out.println(bar);
        System.out.println(bar.getAmount());
    }
}

@Data
class Foo implements Serializable {
    String val;
    String amt;
}

@Data
class Bar implements Serializable {
    String val;
    BigDecimal amount;
}

