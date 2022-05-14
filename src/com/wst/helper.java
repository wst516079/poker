package com.wst;

import java.util.List;

public class helper {
    public String StringHelper(List<List<String>> source, String target, int sub) {
        if (source.size() == 0){
            target = "";
        }
        else if (source.get(sub).size()==0){
            target = "";
        }
        else{
            for (int i = 0; i < source.get(sub).size(); i++){
                target += source.get(sub).get(i);
                target += ";";
            }
        }
        return target;
    }
    public int sumHelper(List<String> temp){
        int res = 0;
        for (int i = 0; i < temp.size(); i++){
            res += Integer.parseInt(temp.get(i));
        }
        return res;
    }
}
