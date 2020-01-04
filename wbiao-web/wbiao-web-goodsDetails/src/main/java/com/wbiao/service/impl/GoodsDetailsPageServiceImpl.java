package com.wbiao.service.impl;

import com.wbiao.service.GoodsDetailsPageService;
import com.wbiao.service.GoodsDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

@Service
public class GoodsDetailsPageServiceImpl implements GoodsDetailsPageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsDetailsPageServiceImpl.class);

    @Autowired
    private TemplateEngine engine;

    @Autowired
    private GoodsDetailsService goodsDetailsService;

    @Override
    public void CreatePage(String skuid) {
        Context context = new Context();

        Map<String, Object> resultMap = goodsDetailsService.goodsDetails(skuid);
        context.setVariable("goodsDetails",resultMap);
        File dir = new File("D:/nginx-1.16.1/html/goodsDetails");
        //判断商品详情页文件夹是否存在,不存在则创建    
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //定义输出流,进行文件生成      
        File file = new File(dir+"/"+skuid+".html");
        Writer printWriter = null;
        try {
            printWriter = new PrintWriter(file);
            engine.process("goodsDetails", context, printWriter);
        } catch (Exception e) {
            LOGGER.error("页面静态化出错：{}，" + e, skuid);
        } finally {
            if (printWriter != null) {
                try {
                    printWriter.close();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }

            }
        }

    }
}
