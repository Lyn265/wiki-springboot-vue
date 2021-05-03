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
      <a-table
              :columns="columns"
              :row-key="record => record.id"
              :data-source="level1"
              :loading="loading"
              :pagination="false"
      >
        <template #cover="{ text: cover }">
          <img v-if="cover" :src="cover" alt="avatar" />
        </template>
<!--        <template v-slot:category="{ text, record }">-->
<!--          <span>{{ getCategoryName(record.category1Id) }}/{{ getCategoryName(record.category2Id) }}</span>-->
<!--        </template>-->
        <template v-slot:action="{ text, record }">
          <a-space size="small">
            <a-button type="primary" @click="edit(record)">
              编辑
            </a-button>
            <a-popconfirm
                    title="删除后无法恢复 确认删除?"
                    ok-text="Yes"
                    cancel-text="No"
                    @confirm="handleDelete(record.id)">
              <a-button type="danger" >
                删除
              </a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </a-table>
    </a-layout-content>
  </a-layout>
  <a-modal
          title="目录表单"
          v-model:visible="modelVisible"
          :confirm-loading="modelLoading"
          @ok="handleOk">
    <a-form :model="category" :label-col="{span:6}" :wrapper-col="{span:18}">
      <a-form-item label="名称">
        <a-input v-model:value="category.name" />

      </a-form-item>
      <a-form-item label="父分类">
<!--        <a-input v-model:value="category.parent"/>-->
        <a-select
                v-model:value="category.parent"
                ref="select"
        >
          <a-select-option :value="c.id" v-for="c in level1" :key="c.id" :disabled="c.id===category.id">{{c.name}}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="顺序">
        <a-input v-model:value="category.sort"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script lang="ts">
  import { defineComponent,ref,onMounted } from 'vue';
  import {message} from "ant-design-vue";
  import axios from 'axios';
  import {Tool} from "@/utils/tool";

  export default defineComponent({
        name: "AdminCategory",
      setup(){
        const category = ref({});
        const modalText = ref<string>('Content of the modal');
        const modelVisible = ref<boolean>(false);
        const modelLoading = ref<boolean>(false);
        const param = ref();
        param.value = {};
        // const categorys = ref();
        const level1 = ref();
        const loading = ref(false);
        const columns = [
          {
            title: '名称',
            dataIndex: 'name'
          },
          {
            title: '父分类',
           dataIndex: 'parent'
          },
          {
            title: '顺序',
            dataIndex: 'sort'
          },
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
        const edit =(record:any) =>{
          const temp = Object.assign({},record);
          modelVisible.value = true;
          category.value = temp;
        };
        const add =() =>{
          modelVisible.value = true;
          category.value = {};
        };

        const handleOk = () => {
          // modalText.value = 'The modal will be closed after two seconds';
          // modelLoading.value = true;
          // setTimeout(() => {
          //   modelVisible.value = false;
          //   modelLoading.value = false;
          // }, 2000);
          axios.post("/category/save",category.value).then((response) => {
            loading.value = false;
            modelLoading.value = false;
            const data = response.data;
            if (data.success){
                modelVisible.value = false;
                message.info("更新成功");
              handleQuery();
              // 刷新页面
            } else {
              message.error(data.message);
            }
          });

        };
        const handleDelete = (id:number) =>{
          axios.delete("/category/delete/"+id).then((resp) =>{
            const data = resp.data;
            if(data.success){
              message.info(data.message);
              handleQuery();
            }else{
              message.error(data.message);
            }
          })
        };
        /**
         * 数据查询
         **/
        const handleQuery = () => {
          loading.value = true;
          //查询数据之前先清空 避免保存新数据后还显示老数据
          axios.get("/category/all").then((response) => {
            loading.value = false;
            const data = response.data;
            level1.value = [];
            level1.value = Tool.array2Tree(data,0);
            // if (data.success){
            //   categorys.value = data.content;
            // }
            // else {
            //   message.error(data.message);
            // }
          });
        };

        onMounted(() =>{
           handleQuery();
        });
        return {
          param,
          level1,
          columns,
          loading,
          modalText,
          modelVisible,
          modelLoading,
          category,

          handleQuery,
          handleDelete,
          handleOk,
          edit,
          add,


        };
      },
    });

</script>

<style scoped>

</style>
