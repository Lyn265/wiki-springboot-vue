<template>
  <a-layout>
    <a-layout-content
            :style="{ background: '#ffffff', padding: '24px', margin: 0, minHeight: '280px' }">
      <p>
        <a-form
                layout="inline"
                :model="param">
          <a-form-item>
            <a-input v-model:value="param.name" placeholder="名称">
            </a-input>
          </a-form-item>
          <a-form-item>
            <a-button
                    type="primary"
                    @click="handleQuery({page:1,size:pagination.pageSize})">
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
              :data-source="ebooks"
              :pagination="pagination"
              :loading="loading"
              @change="handleTableChange"
      >
        <template #cover="{ text: cover }">
          <img v-if="cover" :src="cover" alt="avatar" />
        </template>
        <template v-slot:category="{ text, record }">
          <span>{{ getCategoryName(record.category1Id) }}/{{ getCategoryName(record.category2Id) }}</span>
        </template>
        <template v-slot:action="{ text, record }">
          <a-space size="small">
            <router-link :to="'/admin/doc?ebookId='+record.id">
              <a-button type="primary">
                文档管理
              </a-button>
            </router-link>
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
          title="电子书表单"
          v-model:visible="modelVisible"
          :confirm-loading="modelLoading"
          @ok="handleOk">
    <a-form :model="ebook" :label-col="{span:6}" :wrapper-col="{span:18}">
      <a-form-item label="名称">
        <a-input v-model:value="ebook.name" />
      </a-form-item>
      <a-form-item label="封面">
        <a-input v-model:value="ebook.cover"/>
      </a-form-item>
      <a-form-item label="分类">
        <a-cascader v-model:value="categoryIds" :options="level1" placeholder="Please select"
        :field-names="{label: 'name', value: 'id', children: 'children'}"
        />
      </a-form-item>
      <a-form-item label="描述">
        <a-input v-model:value="ebook.description" />
      </a-form-item>
<!--      <a-form-item label="目录1">-->
<!--        <a-input v-model:value="ebook.category1Id" />-->
<!--      </a-form-item>-->
<!--      <a-form-item label="目录2">-->
<!--        <a-input v-model:value="ebook.category2Id" />-->
<!--      </a-form-item>-->
    </a-form>
  </a-modal>
</template>
<script lang="ts">
  import { defineComponent,ref,onMounted } from 'vue';
  import {message} from "ant-design-vue";
  import axios from 'axios';
  import {Tool} from "@/utils/tool";

    export default defineComponent({
        name: "AdminEbook",
      setup(){
        let categorys:any=[];
        const categoryIds = ref();
        const level1 = ref();
        const ebook = ref();
        const modalText = ref<string>('Content of the modal');
        const modelVisible = ref<boolean>(false);
        const modelLoading = ref<boolean>(false);
        const param = ref();
        param.value = {};
        const ebooks = ref();
        const pagination = ref(
                {
                  current:1,
                  pageSize:5,
                  total:0
                }
        );
        const loading = ref(false);
        const columns = [
          {
            title: '封面',
            dataIndex: 'cover',
            slots: { customRender: 'cover' }
          },
          {
            title: '名称',
            dataIndex: 'name'
          },
          {
            title: '分类',
            slots: {customRender: 'category'}
          },
          {
            title: '文档数',
            dataIndex: 'docCount'
          },
          {
            title: '阅读数',
            dataIndex: 'viewCount'
          },
          {
            title: '点赞数',
            dataIndex: 'voteCount'
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

        //编辑
        const edit =(record:any) =>{
          modelVisible.value = true;
          const temp = Object.assign({},record);
          ebook.value = temp;
          categoryIds.value = [ebook.value.category1Id,ebook.value.category2Id];

        };
        const add =() =>{
          modelVisible.value = true;
          ebook.value = {};
        };
        //[前端开发/Vue] [000/100]
        const handleOk = () => {
          // modalText.value = 'The modal will be closed after two seconds';
          modelLoading.value = true;
          ebook.value.category1Id = categoryIds.value[0];
          ebook.value.category2Id = categoryIds.value[1];
          axios.post("/ebook/save",ebook.value).then((response) => {
            loading.value = false;
            modelLoading.value = false;
            const data = response.data;
            if (data.success){
                modelVisible.value = false;
                message.info("更新成功");
              handleQuery({
                page:pagination.value.current,
                size:5
              });
              // 刷新页面
            } else {
              message.error(data.message);
            }
          });

        };
        //获取目录名字
        const getCategoryName = (cid:any) =>{
          let result = '';
            categorys.forEach((item:any) =>{
              if(item.id === cid){
                result = item.name;
              }
            });
          return result;
        };
        const handleDelete = (id:number) =>{
          axios.delete("/ebook/delete/"+id).then((resp) =>{
            const data = resp.data;
            if(data.success){
              message.info(data.message);
              handleQuery({
                page:pagination.value.current,
                size:5
              });
            }else{
              message.error(data.message);
            }
          })
        };
        /**
         * 数据查询
         **/
        const handleQuery = (params: any) => {
          loading.value = true;
          //查询数据之前先清空 避免保存新数据后还显示老数据
          ebooks.value=[];
          axios.get("/ebook/list",{
            params:{
              page:params.page,
              size:params.size,
              name:param.value.name
            }
          }).then((response) => {
            loading.value = false;
            const data = response.data;
            if (data.success){
              ebooks.value = data.content.list;
              // 重置分页按钮
              pagination.value.current = params.page;
              pagination.value.total = data.content.total;
            }
            else {
              message.error(data.message);
            }
          });
        };
        /**
         * 查询目录
         */
        const handleQueryCategory = () =>{
          axios.get("/category/all").then((response) => {
            loading.value = false;
            categorys = response.data;
            level1.value = [];
            level1.value = Tool.array2Tree(categorys,0);
            handleQuery({
              page: pagination.value.current,
              size: pagination.value.pageSize
            });
          });
        };
        /**
         * 表格点击页码时触发
         */
        const handleTableChange = (pagination: any) => {
          console.log("看看自带的分页参数都有啥：" + JSON.stringify(pagination));
          handleQuery({
            page: pagination.current,
            size: pagination.pageSize
          });
        };
        onMounted(() =>{
          handleQueryCategory();
        });
        return {
          param,
          ebooks,
          columns,
          pagination,
          loading,
          modalText,
          modelVisible,
          modelLoading,
          ebook,
          level1,
          categoryIds,

          getCategoryName,
          handleTableChange,
          handleQuery,
          handleQueryCategory,
          handleDelete,
          edit,
          add,
          handleOk

        };
      },
    });

</script>

<style scoped>

</style>
