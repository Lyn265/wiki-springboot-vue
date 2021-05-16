<template>
 <div>
     <!--上半部分-->
     <a-row>
         <a-col :span="24">
             <a-card>
                <a-row :gutter="16">
                     <a-col :span="8">
                         <a-statistic title="总阅读量" :value="statistic.viewCount" style="margin-right: 50px">
                             <template #suffix>
                                 <UserOutlined />
                             </template>
                         </a-statistic>
                     </a-col>
                     <a-col :span="8">
                         <a-statistic title="总点赞量" :value="statistic.voteCount" style="margin-right: 50px">
                             <template #suffix>
                                 <like-outlined />
                             </template>
                         </a-statistic>
                     </a-col>
                     <a-col :span="8">
                         <a-statistic title="点赞率" :value="statistic.voteCount/statistic.viewCount*100"
                          style="margin-right: 50px"
                          :precision="2"
                          suffix="%"
                          :value-style="{ color: '#cf1322' }"
                         >
                         </a-statistic>
                     </a-col>
                </a-row>
             </a-card>
         </a-col>
     </a-row>
     <br>
     <a-row :gutter="16">
         <!--下半部分 -->
         <a-col :span="12">
             <a-card>
                 <a-row>
                     <a-col :span="12">
                         <a-statistic title="今日阅读量" :value="statistic.todayViewCount" style="margin-right: 50px">
                             <template #suffix>
                                 <UserOutlined />
                             </template>
                         </a-statistic>
                     </a-col>
                     <a-col :span="12">
                         <a-statistic title="今日点赞量" :value="statistic.todayVoteCount">
                             <template #suffix>
                                 <like-outlined />
                             </template>
                         </a-statistic>
                     </a-col>
                 </a-row>
             </a-card>
         </a-col>
         <a-col :span="12">
             <a-card>
                 <a-row>
                     <a-col :span="12">
                             <a-statistic
                                     title="预计今日阅读增长"
                                     :value="statistic.todayViewIncrease"
                                     :value-style="{ color: '#0000fff' }"
                             >
                                 <template #prefix>
                                     <UserOutlined />
                                 </template>
                             </a-statistic>
                     </a-col>
                     <a-col :span="12">
                             <a-statistic
                                     title="预计今日点赞增长"
                                     :value="statistic.todayViewIncreaseRateAbs"
                                     :precision="2"
                                     suffix="%"
                                     class="demo-class"
                                     :value-style="statistic.todayViewIncreaseRate<0 ? { color: '#3f8600' } : { color: '#cf1322' }"
                             >
                                 <template #prefix>
                                     <arrow-up-outlined v-if="statistic.todayViewIncreaseRate >= 0"/>
                                     <arrow-down-outlined v-if="statistic.todayViewIncreaseRate < 0"/>
                                 </template>
                             </a-statistic>
                     </a-col>
                 </a-row>
             </a-card>
         </a-col>
     </a-row>
     <br>
     <a-row>
         <a-col :span="24">
             <div id="main" style="width: 600px;height:400px;"></div>
         </a-col>
     </a-row>
 </div>
</template>

<script lang="ts">
    import {defineComponent, onMounted, ref} from "vue";
    import axios from 'axios';
    declare let echarts:any;
    export default defineComponent({
        name: "TheWelcome",
        setup(){
        const statistic = ref();
        statistic.value = {};
        const list = ref();
        list.value = [];

        const get30Statistic = () =>{
            axios.get('/ebook-snapshot/get-statistic').then(resp =>{
                const data = resp.data;
                if(data.success){
                    const list = data.content;
                    init30DayEcharts(list);
                }
            })
        };

        const init30DayEcharts = (list:any) =>{
            const myChart = echarts.init(document.getElementById('main'));
            const date = [];
            const viewIncrease = [];
            const voteIncrease = [];
            for (let i = 0; i <list.length ; i++) {
                const record = list[i];
                date.push(record.date);
                viewIncrease.push(record.viewIncrease);
                voteIncrease.push(record.voteIncrease);
            }
            const option = {
                title: {
                    text: '折线图堆叠'
                },
                tooltip: {
                    trigger: 'axis'
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: date
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name: '总阅读量',
                        type: 'line',
                        smooth:true,
                        data:viewIncrease
                    },
                    {
                        name: '总点赞量',
                        type: 'line',
                        smooth:true,
                        data: voteIncrease
                    },
                ]
            };
            myChart.setOption(option);

        };
        const getStatistic = () =>{
            axios.get('/ebook-snapshot/get-statistic').then(resp =>{
                const data = resp.data;
                if(data.success){
                const statisticResp = data.content;
                //总阅读量
                statistic.value.viewCount = statisticResp[1].viewCount;
                //总点赞量
                statistic.value.voteCount = statisticResp[1].voteCount;
                //今日阅读量
                statistic.value.todayViewCount = statisticResp[1].viewIncrease;
                //今日点赞量
                statistic.value.todayVoteCount = statisticResp[1].voteIncrease;
                //按分钟来计算当天时间点，占当天的百分比
                    const now = new Date();
                    const nowRate = (now.getHours()*60 + now.getMinutes())/(24*60);
                    console.log(nowRate)
                    //今天预计阅读增长量
                     statistic.value.todayViewIncrease = parseInt(String(statisticResp[1].viewIncrease / nowRate));
                    //今天预计阅读增长比率
                    statistic.value.todayViewIncreaseRate = (statistic.value.todayViewIncrease - statisticResp[0].viewIncrease)/statisticResp[0].viewIncrease*100;
                    console.log(statistic.value.todayViewIncreaseRate);
                    statistic.value.todayViewIncreaseRateAbs = Math.abs(statistic.value.todayViewIncreaseRate);
                }
            })
        };
            onMounted(() =>{
                getStatistic();
                get30Statistic();
                //testChart();
            });


            return {
                statistic,
            }
        },


    })
</script>

<style scoped>

</style>
