<template>
    <a-layout>
        <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">
            <a-row>
                <a-col :span="6">
                    <a-tree
                            v-if="level1.length>0"
                            :tree-data="level1"
                            @select="onSelect"
                            :replaceFields="{title:'name',key:'id',value:'id'}"
                            :defaultExpandAll="true"
                    >
                    </a-tree>
                </a-col>
                <a-col :span="18">
                    <div :innerHTML="html"></div>
                </a-col>
            </a-row>
        </a-layout-content>
    </a-layout>
</template>

<script lang="ts">
    import {defineComponent, onMounted, ref} from 'vue';
    import {useRoute} from "vue-router";
    import axios from "axios";
    import {Tool} from "@/utils/tool";
    import {message} from "ant-design-vue";

    export default defineComponent({
        name: "Doc",
        setup(){
            const route = useRoute();
            const level1 = ref();
            level1.value = [];
            // const docs = ref();
            const html = ref();

            /**
             * 电子书数据查询（根据ebookId查询）
             **/
            const handleQuery = () => {
                //查询数据之前先清空 避免保存新数据后还显示老数据
                level1.value = [];
                axios.get("/doc/all/"+route.query.ebookId).then((response) => {
                    const data = response.data;
                    level1.value = [];
                    level1.value = Tool.array2Tree(data,0);
                    // if (data.success){
                    //   docs.value = data.content;
                    // }
                    // else {
                    //   message.error(data.message);
                    // }
                });
            };
            const handleDocContent = (id:any) => {
                //查询数据之前先清空 避免保存新数据后还显示老数据
                axios.get("/doc/find-content/"+id).then((response) => {
                    const data = response.data;
                    if(data.success){
                        html.value = data.content;
                    }else {
                        message.error(data.message);
                    }
                });
            };
            const onSelect =(selectedKeys:any,info:any) =>{
                console.log("selectedKeys:"+selectedKeys,info);
                handleDocContent(selectedKeys[0]);
            };
            onMounted(() =>{
                handleQuery();
            });
            return{
                level1,
                html,
                onSelect
            }
        },

    })
</script>

<style scoped>

</style>
