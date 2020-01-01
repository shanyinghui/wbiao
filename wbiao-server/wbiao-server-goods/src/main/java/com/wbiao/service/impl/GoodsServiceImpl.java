package com.wbiao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wbiao.mapper.SkuMapper;
import com.wbiao.mapper.SpuMapper;
import com.wbiao.goods.pojo.Goods;
import com.wbiao.goods.pojo.Sku;
import com.wbiao.goods.pojo.Spu;
import com.wbiao.service.GoodsService;
import com.wbiao.service.SpecService;
import com.wbiao.util.IdWorker;
import com.wbiao.util.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService{
    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SpecService specService;

    //分布式id生成解决方案，基于开源算法snowflake
    @Autowired
    private IdWorker idWorker;

    @Transactional
    @Override
    public void addGoods(Goods goods) {
        Spu spu = goods.getSpu();
        Long spu_id = (Long)idWorker.nextId();
        spu.setId(spu_id.toString());
        spu.setCreatetime(new Date());
        spu.setLast_updatetime(new Date());
        //先添加spu
        spuMapper.addSpu(spu);
        long l = idWorker.nextId();
        List<Sku> skus = goods.getSkus();
        //循环添加sku
        if(skus!=null && skus.size()>0){
            for (Sku sku: skus){
                Long sku_id = (Long)idWorker.nextId();
                sku.setId(sku_id.toString());
                sku.setSpu_id(spu_id.toString());
                sku.setBrand(spu.getBrand());
                if(StringUtils.isNotBlank(sku.getImages())){
                    String[] images = sku.getImages().split(",");
                    sku.setImage(images[0]);
                }
                sku.setComment_num(0L);
                sku.setSale_num(0L);
                sku.setCreatetime(new Date());
                sku.setLast_updatetime(new Date());

                skuMapper.addSku(sku);
                //向规格参数表添加规格值，如存在就不添加
                specService.addSpec_val(sku.getOwnSpec());
            }
        }
    }

    @Override
    public PageResult<Sku> selectGoods(String key, Boolean saleable, Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        List<Sku> skus = skuMapper.selectSku(key, saleable);
        PageInfo<Sku> pageInfo = new PageInfo<>(skus);
        PageResult<Sku> pr = new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
        return pr;
    }

    @Override
    public Spu selectSpuById(String id) {
        return spuMapper.selectSpuById(id);
    }

    @Override
    public List<Sku> selectSkuBySpu_id(String id) {
        return skuMapper.selectSkuBySpu_id(id);
    }

    @Transactional
    @Override
    public void updateGoods(Goods goods) {
        Spu spu = goods.getSpu();
        List<Sku> skus = goods.getSkus();

        spu.setLast_updatetime(new Date());
        //修改spu表信息
        spuMapper.updateSpuById(spu);
        //删除所有的sku
        skuMapper.deleteSkuBySpu_id(spu.getId());
        //删除完循环添加
        if (skus != null && skus.size() > 0) {
            for (Sku sku : skus) {
                //判断sku的id是否存在，如存在则此sku不是新添加的
                if (StringUtils.isNotBlank(sku.getId())) {
                    sku.setSpu_id(spu.getId());
                    sku.setBrand(spu.getBrand());
                    sku.setLast_updatetime(new Date());
                    if (StringUtils.isNotBlank(sku.getImages())) {
                        String[] images = sku.getImages().split(",");
                        sku.setImage(images[0]);
                    }
                    skuMapper.addSku(sku);
                    specService.addSpec_val(sku.getOwnSpec());
                } else { //此sku是新添加的
                    Long sku_id = (Long) idWorker.nextId();
                    sku.setId(sku_id.toString());
                    sku.setSpu_id(spu.getId());
                    sku.setBrand(spu.getBrand());
                    if (StringUtils.isNotBlank(sku.getImages())) {
                        String[] images = sku.getImages().split(",");
                        sku.setImage(images[0]);
                    }
                    sku.setComment_num(0L);
                    sku.setSale_num(0L);
                    sku.setCreatetime(new Date());
                    sku.setLast_updatetime(new Date());
                    skuMapper.addSku(sku);
                    specService.addSpec_val(sku.getOwnSpec());
                }
            }
        }
    }

    @Override
    public void deleteSkuById(String id) {
        skuMapper.deleteSkuById(id);
    }

    @Override
    public void updateSkuEnable(String id) {
        skuMapper.updateSkuEnable(id);
    }

    @Override
    public List<Sku> selectSkuByEnable() {
        return skuMapper.selectSkuByEnable();
    }
}
