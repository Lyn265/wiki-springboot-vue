<template>
  <a-layout>
    <a-layout-sider width="200" style="background: #fff">
      <a-menu
              mode="inline"
              :style="{ height: '100%', borderRight: 0 }"
              @click="handleClick"
      >
        <a-menu-item key="welcome">
          <span><user-outlined />欢迎</span>
        </a-menu-item>
        <a-sub-menu v-for="item in level1" :key="item.id">
          <template v-slot:title>
            <span><user-outlined />{{item.name}}</span>
          </template>
          <a-menu-item :key="item2.id" v-for="item2 in item.children">{{item2.name}}</a-menu-item>
        </a-sub-menu>
      </a-menu>
    </a-layout-sider>
    <a-layout style="padding: 0 24px 24px">
      <a-breadcrumb style="margin: 16px 0">
        <a-breadcrumb-item>Home</a-breadcrumb-item>
        <a-breadcrumb-item>List</a-breadcrumb-item>
        <a-breadcrumb-item>App</a-breadcrumb-item>
      </a-breadcrumb>
      <a-layout-content
              :style="{ background: '#fff', padding: '24px',
              margin: 0, minHeight: '280px' }"
      >
        <div class="welcome" v-show="isShowWelcome">
          <h1>欢迎来到我的电子书网站</h1>
        </div>
        <a-list v-show="!isShowWelcome" item-layout="vertical" size="large" :grid="{gutter:20,column:3}" :data-source="eBooks">
          <template #renderItem="{ item }">
            <a-list-item key="item.name">
              <template #actions>
          <span v-for="{ type, text } in actions" :key="type">
            <component v-bind:is="type" style="margin-right: 8px" />
            {{ text }}
          </span>
              </template>
              <a-list-item-meta :description="item.description">
                <template #title>
                  <a :href="item.href">{{ item.name }}</a>
                </template>
                <template #avatar><a-avatar :src="item.cover" /></template>
              </a-list-item-meta>
            </a-list-item>
          </template>
        </a-list>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script lang="ts">
  import { defineComponent,onMounted ,ref} from 'vue';
  import { StarOutlined, LikeOutlined, MessageOutlined } from '@ant-design/icons-vue';
  import axios from 'axios';
  import {Tool} from "@/utils/tool";

export default defineComponent({
  name: 'Home',
  components: {
    StarOutlined,
    LikeOutlined,
    MessageOutlined,
  },
  setup(){
    const level1 = ref();
    const eBooks = ref();//ref:响应式数据
    const isShowWelcome = ref<boolean>(true);
    let categorys:any=[];
    let categoryId2 = 0;
    const handleCategoryQuery = () => {
      //查询数据之前先清空 避免保存新数据后还显示老数据
      axios.get("/category/all").then((response) => {
        categorys = response.data;
        level1.value = [];
        level1.value = Tool.array2Tree(categorys,0);
      });
    };
    const queryEbookbyCategory = () =>{
      axios.get("/ebook/list",{
        params:{
          page:1,
          size:1000,
          categoryId2,
        }
      }).then(resp =>{
        const data = resp.data;
        eBooks.value = data.content.list;
      });
    };
    const handleClick = (val:any) =>{
      if(val.key ==='welcome'){
        isShowWelcome.value = true;
      }else{
        categoryId2 = val.key;
        isShowWelcome.value = false;
        queryEbookbyCategory();
      }
      // isShowWelcome.value = val === 'welcome';
    };
    onMounted(() =>{
      handleCategoryQuery();
    });
    const actions: Record<string, string>[] = [
      { type: 'StarOutlined', text: '156' },
      { type: 'LikeOutlined', text: '156' },
      { type: 'MessageOutlined', text: '2' },
    ];
    return{
      eBooks,
      actions,
      level1,
      isShowWelcome,
      handleClick,
      collapsed: ref<boolean>(false),
    }
  }
});
</script>
<style scoped>
  .ant-avatar{
    width: 50px;
    height: 50px;
    line-height: 50px;
    border-radius: 8%;
    margin:5px 0;
  }
</style>
