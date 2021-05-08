<template>
  <a-layout>
    <a-layout-content
            :style="{ background: '#ffffff', padding: '24px', margin: 0, minHeight: '280px' }">
      <p>
        <a-form
                layout="inline"
                :model="param">
          <a-form-item>
            <a-button
                    type="primary"
                    @click="handleQuery()">
              查询
            </a-button>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="add()" >
              新增
            </a-button>
          </a-form-item>
        </a-form>
      </p>
        <a-row :gutter="24">
            <a-col :span="8">
                <a-table
                        v-if="level1.length > 0"
                        :columns="columns"
                        :row-key="record => record.id"
                        :data-source="level1"
                        :loading="loading"
                        :pagination="false"
                        size="small"
                        :defaultExpandAllRows="true"
                >
                    <template #name="{ text,record }">
                         {{text}}
                    </template>
                    <!--        <template v-slot:doc="{ text, record }">-->
                    <!--          <span>{{ getDocName(record.doc1Id) }}/{{ getDocName(record.doc2Id) }}</span>-->
                    <!--        </template>-->
                    <template v-slot:action="{ text, record }">
                        <a-space size="small">
                            <a-button type="primary" @click="edit(record)" size="small">
                                编辑
                            </a-button>
                            <a-popconfirm
                                    title="删除后无法恢复 确认删除?"
                                    ok-text="Yes"
                                    cancel-text="No"
                                    @confirm="handleDelete(record.id)">
                                <a-button type="danger" size="small">
                                    删除
                                </a-button>
                            </a-popconfirm>
                        </a-space>
                    </template>
                </a-table>
            </a-col>
            <a-col :span="16">
                <p>
                    <a-form layout="inline">
                        <a-form-item>
                            <a-button type="primary" @click="handleSave" size="small">
                                保存
                            </a-button>
                        </a-form-item>
                    </a-form>
                </p>

                <a-form :model="doc" layout="vertical">
                    <a-form-item>
                        <a-input v-model:value="doc.name" />
                    </a-form-item>
                    <a-form-item>
                        <a-tree-select
                                v-model:value="doc.parent"
                                style="width: 100%"
                                :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
                                :tree-data="treeSelectData"
                                :replaceFields="{title:'name',key:'id',value:'id'}"
                                placeholder="请选择父文档"
                                tree-default-expand-all
                        >
                        </a-tree-select>
                    </a-form-item>
                    <a-form-item>
                        <!--        <a-input v-model:value="doc.parent"/>-->
                        <a-select
                                v-model:value="doc.parent"
                                ref="select">
                            <a-select-option value="0">无</a-select-option>
                            <a-select-option :value="c.id" v-for="c in level1" :key="c.id" :disabled="c.id===doc.id">{{c.name}}</a-select-option>
                        </a-select>
                    </a-form-item>
                    <a-form-item>
                        <a-input v-model:value="doc.sort"/>
                    </a-form-item>
                    <a-form-item>
                        <a-button type="primary" @click="handlePreviewContent()">
                        在线预览
                        </a-button>
                    </a-form-item>
                    <a-form-item>
                        <!--        <a-input v-model:value="doc.sort"/>-->
                        <div id="content"></div>
                    </a-form-item>
                </a-form>
            </a-col>
        </a-row>
        <a-drawer
                width="900"
                placement="right"
                :closable="false"
                :visible="drawerVisible"
                @close="onClose"
        >
            <div class="wangeditor" :innerHTML="previewHtml"></div>
        </a-drawer>
    </a-layout-content>
  </a-layout>
<!--  <a-modal-->
<!--          title="文档表单"-->
<!--          v-model:visible="modelVisible"-->
<!--          :confirm-loading="modelLoading"-->
<!--          @ok="handleOk">-->

<!--  </a-modal>-->
</template>

<script lang="ts">
  import { defineComponent,ref,onMounted,createVNode } from 'vue';
  import {message,Modal} from "ant-design-vue";
  import { ExclamationCircleOutlined } from '@ant-design/icons-vue';
  import axios from 'axios';
  import {Tool} from "@/utils/tool";
  import {useRoute} from "vue-router";
  import E from 'wangeditor';

  export default defineComponent({
        name: "AdminDoc",
      setup(){
        const urlParams  = useRoute();
        const editor = new E('#content');
        const doc = ref();
        doc.value = {ebookId:urlParams.query.ebookId};
        const treeSelectData = ref();
        treeSelectData.value=[];
        const param = ref();
        param.value = {};
        const docs = ref();
        const route  = useRoute();
        const level1 = ref();
        level1.value = [];
        let ids:Array<string> = [];
        let docNames:Array<string> = [];
        const loading = ref(false);
        const previewHtml = ref();
        editor.config.zIndex = 0;
        const  drawerVisible = ref<boolean>(false);
        const columns = [
          {
            title: '名称',
            dataIndex: 'name',
            slots: {customRender: 'name'}
          },
          // {
          //   title: '父文档',
          //  dataIndex: 'parent'
          // },
          // {
          //   title: '顺序',
          //   dataIndex: 'sort'
          // },
          {
            title: 'Action',
            key: 'action',
            slots: { customRender: 'action' }
          }
        ];

        /**
         *
         * 表单相关
         *
         **/
        const showDeleteConfirm = () => {
          Modal.confirm({
            title: '重要提示',
            icon: createVNode(ExclamationCircleOutlined),
            content: "确实要删除【"+docNames.join(',')+"】吗",
            okText: 'Yes',
            okType: 'danger',
            cancelText: 'No',
            onOk() {
              axios.delete("/doc/delete/"+ids.join(',')).then((resp) =>{
                const data = resp.data;
                if(data.success){
                  message.info(data.message);
                  handleQuery();
                }else{
                  message.error(data.message);
                }
              })
            },
            onCancel() {
              console.log('Cancel');
            },
          });
        };
        const setDisable = (treeSelectData:any,id:any) =>{
          //遍历当前树形对象
          for (let i = 0; i <treeSelectData.length ; i++) {
              let node = treeSelectData[i];
              //如果当前节点是目标节点
              if(node.id === id){
                //禁用此节点
                node.disabled = true;
                //查找children下的节点,也就是所有子节点全部都加上disabled
                let children = node.children;
                if(!Tool.isEmpty(children)){
                  for (let j = 0; j <children.length ; j++) {
                    setDisable(children,children[j].id);
                  }
                }
              }else {
                //如果当前父级节点没有，再到子节点查找看看
                const children = node.children;
                if(!Tool.isEmpty(children)){
                  setDisable(children,id);
                }
              }
          }
        };
        const setDelIds = (level1Val:any,id:any) =>{
          //遍历当前树形对象
          for (let i = 0; i <level1Val.length ; i++) {
            let node = level1Val[i];
            //如果当前节点是目标节点
            if(node.id === id){
              ids.push(id);
              docNames.push(node.name);
              //查找children下的节点,也就是所有子节点全部都加上disabled
              let children = node.children;
              if(!Tool.isEmpty(children)){
                for (let j = 0; j <children.length ; j++) {
                  setDelIds(children,children[j].id);
                }
              }
            }else {
              //如果当前父级节点没有，再到子节点查找看看
              const children = node.children;
              if(!Tool.isEmpty(children)){
                  setDelIds(children,id);
              }
            }
          }
        };

        /**
         * 编辑
         * 不能选择当前节点以及所有子孙节点作为父节点,会使树枝断开。
         **/
        const edit =(record:any) =>{
          editor.txt.html("");
          const temp = Object.assign({},record);
          doc.value = temp;
          handleDocContent();
          // doc.value.content = editor.txt.html();
          //复制level1到treeSelectData,并添加 '无' 这个节点
          treeSelectData.value =Tool.copy(level1.value);
          //禁用选择当前节点以及所有子孙节点作为父节点
          setDisable(treeSelectData.value,record.id);
          treeSelectData.value.unshift({id:0,name:'无'});
        };
        /**
         *新增
         *
         **/
        const add =() =>{
          editor.txt.html("");
          // doc.value = {ebookId:urlParams.query.ebookId};
          //复制level1到treeSelectData,并添加 '无' 这个节点
        };
        //电子书保存（包括富文本）
          const handleSave = () => {
          doc.value.content = editor.txt.html();
          axios.post("/doc/save",doc.value).then((response) => {
            loading.value = false;
            const data = response.data;
            if (data.success){
                message.info("保存成功");
              handleQuery();
              // 刷新页面
            } else {
              message.error(data.message);
            }
          });

        };
        /**
         * 删除父节点的同时也删除子节点的id
         *
         **/
        const handleDelete = (id:any) =>{
          ids = [];
          docNames = [];
          setDelIds(level1.value,id);
          showDeleteConfirm();

        };
        /**
         * 电子书数据查询（doc）
         **/
        const handleQuery = () => {
          loading.value = true;
          //查询数据之前先清空 避免保存新数据后还显示老数据
          axios.get("/doc/all/"+route.query.ebookId).then((response) => {
            loading.value = false;
            const data = response.data;
            if (data.success){
                level1.value = [];
                level1.value = Tool.array2Tree(data.content,0);
                treeSelectData.value = Tool.copy(level1.value) || [];
                treeSelectData.value.unshift({id:0,name:'无'});
              docs.value = data.content;
            }
            else {
              message.error("获取电子书失败。");
            }
          });
        };
          /**
           * 电子书数据查询（富文本内容）
           **/
          const handleDocContent = () => {
              loading.value = true;
              //查询数据之前先清空 避免保存新数据后还显示老数据
              axios.get("/doc/find-content/"+doc.value.id).then((response) => {
                  loading.value = false;
                  if(response.data.success){
                      const content = response.data.content;
                      editor.txt.html(content);
                  }
              });
          };
          const handlePreviewContent = () => {
              const html = editor.txt.html();
              previewHtml.value = html;
              drawerVisible.value = true;
          };

          const onClose = () => {
              drawerVisible.value = false;
          };

        onMounted(() =>{
           handleQuery();
            editor.create();
        });
        return {
          param,
          level1,
          treeSelectData,
          columns,
          loading,
          doc,
          drawerVisible,
          previewHtml,

          handlePreviewContent,
          onClose,
          handleQuery,
          handleDelete,
          handleSave,
          edit,
          add,
          showDeleteConfirm,


        };
      },
    });

</script>

<style scoped>

</style>
