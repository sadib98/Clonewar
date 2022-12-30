<template>
    <form method="post" @submit.prevent="postFilePath">
        <table>
            <tr>
                <td>File path: </td>
                <td><input type="text" id="path" v-model="path" size="80" /></td>
            </tr>
            <tr>
                <td><button>Add</button></td>
            </tr>
        </table>
    </form>
</template>

<script>
import axios from 'axios'

const apiURL = "api/files/upload";

export default {
    name: 'PostFilePath',
    data(){
        return {
            path: ''
        }
    },
    methods: {
        postFilePath(){
            console.log(this.path);
            /*axios({
                method: 'post',
                url: 'http://localhost:8080/api/files/upload',
                header:  ('Content-type: application/json'),
                data: this.path
            })
            .then(res => console.log(res))
            .then(error => console.log(error));*/
            
            fetch('api/files/upload', {
                method: 'POST',
                /*body: JSON.stringify( this.path )*/
                body: this.path
            } )
            .then( function( response ){
                if( response.status != 201 ){
                    this.fetchError = response.status;
                }else{
                    response.json().then( function( data ){
                        this.fetchResponse = data;
                    }.bind(this));
                }
            }.bind(this))
        }
    }
    
}
</script>

