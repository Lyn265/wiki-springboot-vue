
export class Tool {

    public static isEmpty(obj:any){
        if(typeof obj == 'string'){
            return !obj || obj.replace(/\s+/g, "") === "";
        }else{
            return (!obj || JSON.stringify(obj)==="{}" || obj.length===0)
        }
    }

    public static isNotEmpty(obj:any){
        return !this.isEmpty(obj);
    }

    public static copy(obj:object){
        if(!Tool.isEmpty(obj)){
            return JSON.parse(JSON.stringify(obj));
        }
    }

    /**
     * 随机生成[len]长度的[radix]进制数
     * @param len
     * @param radix 默认62
     * @returns {string}
     */
    public static uuid (len: number, radix = 62) {
        const chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
        const uuid = [];
        radix = radix || chars.length;

        for (let i = 0; i < len; i++) {
            uuid[i] = chars[0 | Math.random() * radix];
        }

        return uuid.join('');
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


