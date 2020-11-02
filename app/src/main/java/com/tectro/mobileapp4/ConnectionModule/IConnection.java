package com.tectro.mobileapp4.ConnectionModule;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface IConnection
{
    //some interaction methods witch abstracts other fragment
    void Update(String Key, Object value);
}
