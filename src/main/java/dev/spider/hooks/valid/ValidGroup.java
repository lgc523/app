package dev.spider.hooks.valid;

public class ValidGroup {
    public interface DefaultGroup {

    }

    public interface FooGroup extends DefaultGroup {

    }

    public interface BarGroup extends FooGroup {

    }
}
