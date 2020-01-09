package com.qiuxs.exam.action;

import com.qiuxs.base.action.BaseAction;
import com.qiuxs.base.util.Strings;
import com.qiuxs.exam.entity.ScoreLevel;
import com.qiuxs.exam.entity.WordModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;


@Controller
@Scope("prototype")
public class WordModelAction extends BaseAction {

    public String modelList(){

        List<WordModel> modelList = baseService.getAll(WordModel.class);

        put("modelList",modelList);

        return "modelList";
    }
    public String modelEdit(){

        WordModel model = getEntity(WordModel.class,getInt("pair.id"));

        put("model",model);

        return "modelForm";
    }

    public void modelSave() throws Exception {

        try {


            String indexStr = getString("index");

            if (Strings.isEmpty(indexStr)){
                writeFail("请维护阶段成绩");
                return;
            }

            List<Integer> indexList = Arrays.asList(Strings.splitToInt(indexStr));

            WordModel model = getEntity(WordModel.class,getInt("model.id"));
            model.setName(getString("model.name"));
            model.setCode(getString("model.code"));
            model.getLevels().clear();
            for (Integer index : indexList) {
                ScoreLevel level = new ScoreLevel();
                level.setModel(model);
                level.setName(getString("name_"+index));
                level.setMax(getDouble("max_"+index));
                level.setMin(getDouble("min_"+index));
                level.setPercent(getBoolean("percent_"+index));
                level.setSort(getInt("sort_"+index));
                model.getLevels().add(level);
            }
            baseService.saveOrUpdate(model);
            writeSuccese("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            writeFail(e.getMessage());
        }

    }

    public void modelRemove(){

        try {
            baseService.remove(WordModel.class,getInt("pair.id"));
            writeSuccese("删除成功");
        } catch (Exception e) {
            writeFail(e.getMessage());
        }

    }
}
