<template>
  <div>
    <h2 id="page-heading" data-cy="FieldHeading">
      <span id="field-heading">Fields</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
        <router-link :to="{ name: 'FieldCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-field"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span>Create a new Field</span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && fields && fields.length === 0">
      <span>No Fields found</span>
    </div>
    <div class="table-responsive" v-if="fields && fields.length > 0">
      <table class="table table-striped" aria-describedby="fields">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Name</span></th>
            <th scope="row"><span>Data Type</span></th>
            <th scope="row"><span>Is Unique</span></th>
            <th scope="row"><span>Max Length</span></th>
            <th scope="row"><span>Min Length</span></th>
            <th scope="row"><span>Min Value</span></th>
            <th scope="row"><span>Max Value</span></th>
            <th scope="row"><span>Is Required</span></th>
            <th scope="row"><span>Transfer Core Mapping</span></th>
            <th scope="row"><span>Sort Order</span></th>
            <th scope="row"><span>Form Ui</span></th>
            <th scope="row"><span>Field UI Meta Data</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="field in fields" :key="field.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'FieldView', params: { fieldId: field.id } }">{{ field.id }}</router-link>
            </td>
            <td>{{ field.name }}</td>
            <td>{{ field.dataType }}</td>
            <td>{{ field.isUnique }}</td>
            <td>{{ field.maxLength }}</td>
            <td>{{ field.minLength }}</td>
            <td>{{ field.minValue }}</td>
            <td>{{ field.maxValue }}</td>
            <td>{{ field.isRequired }}</td>
            <td>{{ field.transferCoreMapping }}</td>
            <td>{{ field.sortOrder }}</td>
            <td>
              <div v-if="field.formUi">
                <router-link :to="{ name: 'FormUiView', params: { formUiId: field.formUi.id } }">{{ field.formUi.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="field.fieldUIMetaData">
                <router-link :to="{ name: 'FieldUIMetaDataView', params: { fieldUIMetaDataId: field.fieldUIMetaData.id } }">{{
                  field.fieldUIMetaData.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'FieldView', params: { fieldId: field.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'FieldEdit', params: { fieldId: field.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(field)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="thirdPartyIntegrationApplicationApp.field.delete.question" data-cy="fieldDeleteDialogHeading"
          >Confirm delete operation</span
        >
      </template>
      <div class="modal-body">
        <p id="jhi-delete-field-heading">Are you sure you want to delete Field {{ removeId }}?</p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-field"
            data-cy="entityConfirmDeleteButton"
            v-on:click="removeField()"
          >
            Delete
          </button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./field.component.ts"></script>
