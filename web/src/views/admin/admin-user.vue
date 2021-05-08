<template>
  <a-layout>
    <a-layout-content
            :style="{ background: '#ffffff', padding: '24px', margin: 0, minHeight: '280px' }">
      <p>
        <a-form
                layout="inline"
                :model="param">
          <a-form-item>
            <a-input v-model:value="param.loginName" placeholder="登陆名">
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
              :data-source="users"
              :pagination="pagination"
              :loading="loading"
              @change="handleTableChange"
      >
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
          title="用户表单"
          v-model:visible="modelVisible"
          :confirm-loading="modelLoading"
          @ok="handleOk">
    <a-form :model="user" :label-col="{span:6}" :wrapper-col="{span:18}">
      <a-form-item label="登陆名">
        <a-input v-model:value="user.loginName" :disabled="!!user.id"/>
      </a-form-item>
      <a-form-item label="昵称">
        <a-input v-model:value="user.name"/>
      </a-form-item>
      <a-form-item label="密码">
        <a-input v-model:value="user.password" />
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
        name: "AdminUser",
      setup(){
        const level1 = ref();
        const user = ref();
        const modalText = ref<string>('Content of the modal');
        const modelVisible = ref<boolean>(false);
        const modelLoading = ref<boolean>(false);
        const param = ref();
        param.value = {};
        const users = ref();
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
            title: '登陆名',
            dataIndex: 'loginName'
          },
          {
            title: '昵称',
            dataIndex: 'name'
          },
          {
            title: '密码',
            dataIndex: 'password'
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
          user.value = temp;

        };
        const add =() =>{
          modelVisible.value = true;
          user.value = {};
        };
        //[前端开发/Vue] [000/100]
        const handleOk = () => {
          // modalText.value = 'The modal will be closed after two seconds';
          modelLoading.value = true;
          axios.post("/user/save",user.value).then((response) => {
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
        const handleDelete = (id:number) =>{
          axios.delete("/user/delete/"+id).then((resp) =>{
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
          users.value=[];
          axios.get("/user/list",{
            params:{
              page:params.page,
              size:params.size,
              loginName:param.value.loginName
            }
          }).then((response) => {
            loading.value = false;
            const data = response.data;
            if (data.success){
              users.value = data.content.list;
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
          handleQuery( {page:pagination.value.current,
                  size:5});
        });
        return {
          param,
          users,
          columns,
          pagination,
          loading,
          modalText,
          modelVisible,
          modelLoading,
          user,
          level1,

          handleTableChange,
          handleQuery,
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
