<template>
  <div>
    <h2 id="page-heading" data-cy="FormUiHeading">
      <span id="form-ui-heading">Form Uis</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
        <router-link :to="{ name: 'FormUiCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-form-ui"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span>Create a new Form Ui</span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && formUis && formUis.length === 0">
      <span>No Form Uis found</span>
    </div>
    <div class="table-responsive" v-if="formUis && formUis.length > 0">
      <table class="table table-striped" aria-describedby="formUis">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Form Name</span></th>
            <th scope="row"><span>Form Description</span></th>
            <th scope="row"><span>Form Type</span></th>
            <th scope="row"><span>Step Order</span></th>
            <th scope="row"><span>Third Party Integration</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="formUi in formUis" :key="formUi.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'FormUiView', params: { formUiId: formUi.id } }">{{ formUi.id }}</router-link>
            </td>
            <td>{{ formUi.formName }}</td>
            <td>{{ formUi.formDescription }}</td>
            <td>{{ formUi.formType }}</td>
            <td>{{ formUi.stepOrder }}</td>
            <td>
              <div v-if="formUi.thirdPartyIntegration">
                <router-link
                  :to="{ name: 'ThirdPartyIntegrationView', params: { thirdPartyIntegrationId: formUi.thirdPartyIntegration.id } }"
                  >{{ formUi.thirdPartyIntegration.id }}</router-link
                >
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'FormUiView', params: { formUiId: formUi.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'FormUiEdit', params: { formUiId: formUi.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(formUi)"
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
        <span id="thirdPartyIntegrationApplicationApp.formUi.delete.question" data-cy="formUiDeleteDialogHeading"
          >Confirm delete operation</span
        >
      </template>
      <div class="modal-body">
        <p id="jhi-delete-formUi-heading">Are you sure you want to delete Form Ui {{ removeId }}?</p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-formUi"
            data-cy="entityConfirmDeleteButton"
            v-on:click="removeFormUi()"
          >
            Delete
          </button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./form-ui.component.ts"></script>
