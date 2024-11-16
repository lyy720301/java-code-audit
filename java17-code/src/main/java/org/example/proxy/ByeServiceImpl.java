package org.example.proxy;

public class ByeServiceImpl implements ByeService{
    @Override
    public void sayBye(String name) {
        System.out.println("say bye~");
    }
}
