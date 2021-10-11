<?php
    //Simples exemplo de validação com client Android Mobile
    $inputJSON = file_get_contents('php://input');
    $data      = json_decode($inputJSON, TRUE); 
    
    //Criar método de validação com banco de dados
    if(($data['UserName']=="admin") && ($data['UserPwd']=='123456')){
        header("HTTP/1.1 200 OK");
        $response['status']="ok";
        //Criar método de controle de token
        $response['Token']="1z2x3c4v5b6n7mm7n6b5v4c3x2z1";  
    	echo json_encode($response);
    }else{
        header("HTTP/1.1 404 Not Found");
        $response['error']="Usuário ou senha inválida.";
    	echo json_encode($response);
    }
?>


