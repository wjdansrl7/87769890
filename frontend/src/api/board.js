import {localAxios} from "@/util/http-commons.js";

const local = localAxios();

// 게시판 전체 반환
async function listArticle(param, success, fail) {
    local.get(`/boards/view`, { params: param }).then(success).catch(fail);
}

function detailArticle(articleno, success, fail) {
    local.get(`/boards/${articleno}`).then(success).catch(fail);
}

async function registArticle(formData, success, fail) {
    try {
        const response = await local.post("/boards", formData, {
            headers: { "Content-Type": "multipart/form-data" },
            withCredentials: true
        });
        success(response);
    } catch (error) {
        fail(error);
    }
}

function getModifyArticle(articleno, success, fail) {
    local.get(`/boards/${articleno}`).then(success).catch(fail);
}

function modifyArticle(id, formData, success, fail) {
    local.patch(`/boards/${id}`, formData, {
        headers: { "Content-Type": "multipart/form-data" },
        withCredentials: true,
    }).then(success).catch(fail);
}

function deleteArticle(articleno, success, fail) {
    local.delete(`/boards/${articleno}`).then(success).catch(fail);
}

export {
    listArticle,
    detailArticle,
    registArticle,
    getModifyArticle,
    modifyArticle,
    deleteArticle,
};