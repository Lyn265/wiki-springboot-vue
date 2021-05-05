'use strict';

export class Tool {

    public static isEmpty(obj:any){
        if(typeof obj == 'string'){
            return !obj || obj.replace(/\s+/g, "") === "";
        }else{
            return (!obj || JSON.stringify(obj)==="{}" || obj.length===0)
        }
    }

    public static copy(obj:object){
        if(!Tool.isEmpty(obj)){
            return JSON.parse(JSON.stringify(obj));
        }
    }



    // [{
    //   id:'',
    //   name:'',
    //   children:
    //          [{
    //           id:'',
    //           name:'',
    //          }]
    //
    //  }]

    public static array2Tree(array:any,parentId:number){
        const result=[];
        if(Tool.isEmpty(array)){
            return [];
        }
        for (let i = 0; i <array.length; i++) {
            const c = array[i];
            if(Number(c.parent) === Number(parentId)){
                result.push(c);
                const children = Tool.array2Tree(array,c.id);
                if(!Tool.isEmpty(children)){
                    c.children = children;
                }
            }
        }
        return result;
    }
}


